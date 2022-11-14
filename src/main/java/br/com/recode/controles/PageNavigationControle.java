package br.com.recode.controles;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.recode.entidades.AlertaPreco;
import br.com.recode.entidades.ContatoEmail;
import br.com.recode.entidades.Destino;
import br.com.recode.entidades.Usuario;
import br.com.recode.entidades.ViagemAgendada;
import br.com.recode.servico.AlertaPrecoServico;
import br.com.recode.servico.ContatoEmailServico;
import br.com.recode.servico.DestinoServico;
import br.com.recode.servico.UsuarioDetailsService;
import br.com.recode.servico.ViagemAgendadaServico;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageNavigationControle {
	
	private final DestinoServico destinoServico;
	private final UsuarioDetailsService usuarioDetailsService;
	private final ViagemAgendadaServico viagemAgendadaServico;
	private final ContatoEmailServico contatoEmailServico;
	private final AlertaPrecoServico alertaPrecoServico;
	
	//--------------------------------------------------------
	//Get
	//--------------------------------------------------------
	
	@GetMapping({"/", "/index"})
    public ModelAndView index(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("index.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
    }
	
	@GetMapping("/destinos")
	public ModelAndView  destinos(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("destinos.html");
		List<Destino> destinos = destinoServico.listarTodosLista();
		modelAndView.addObject("destinos", destinos);
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
    }
	
	@GetMapping("/promocoes")
	public ModelAndView  promocoes(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("promocoes.html");
		List<Destino> destinos = destinoServico.listarPromocao();
		modelAndView.addObject("destinos", destinos);
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
    }
	
	@GetMapping("/agendar/{id}")
    public ModelAndView detalhar(@PathVariable Long id, Principal principal) {
		String paginaView = "";
		if(principal == null) {
			paginaView = "login.html";
		} else if(principal != null) {
			paginaView = "agendar.html";
		}
        ModelAndView modelAndView = new ModelAndView(paginaView);
        modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
        Destino destino = destinoServico.buscarPorId(id);
        modelAndView.addObject("destino", destino);
        modelAndView.addObject("ViagemAgendada", new ViagemAgendada());
        modelAndView.addObject("AlertaPreco", new AlertaPreco());
        return modelAndView;
	}
	
	@GetMapping("/alerta/{id}")
	 public ModelAndView alerta(@PathVariable Long id, Principal principal) {
		AlertaPreco alerta = alertaPrecoServico.removerNotificacao(id);
		String paginaView = "redirect:/page/agendar/" + alerta.getDestino().getId();
        ModelAndView modelAndView = new ModelAndView(paginaView);
        
        return modelAndView;
	}
	
	@GetMapping("/contato")
    public ModelAndView contato(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("contato.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		modelAndView.addObject("contatoEmail", new ContatoEmail());
	    return modelAndView;
    }
	
	@GetMapping("/cadastroUsuario")
    public ModelAndView cadastroUsuario(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("cadastroUsuario.html");
		modelAndView.addObject("usuario", new Usuario());
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
    }
	
	@GetMapping("/dadosUsuario")
    public ModelAndView dadosUsuario(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("dadosUsuario.html");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
		modelAndView.addObject("usuario", usuarioLogado);
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
    }
	@GetMapping("/meusAlertas")
    public ModelAndView alertasUsuario(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("alertasUsuario.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
		List<AlertaPreco> alertasPreco = alertaPrecoServico.listarTodosUsuario(usuarioLogado);
		modelAndView.addObject("alertasPreco", alertasPreco);
	    return modelAndView;
    }
	
	@GetMapping("/minhasViagens")
    public ModelAndView viagensUsuario(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("viagensUsuario.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
		List<ViagemAgendada> viagensAgendadas = viagemAgendadaServico.listarTodosUsuario(usuarioLogado);
		modelAndView.addObject("viagensAgendadas", viagensAgendadas);
	    return modelAndView;
    }
	
	//--------------------------------------------------------
	//Post
	//--------------------------------------------------------
	
	@PostMapping("/cadastrarUsuario")
    public ModelAndView cadastrar(Usuario usuario){
        ModelAndView modelAndView = new ModelAndView("redirect:/page/");
        usuario.setAuthorities("ROLE_USER");
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        log.info(usuario);
        usuarioDetailsService.cadastrarUsuario(usuario);
       return modelAndView;
    }
	
	@PostMapping("/enviarMensagem")
    public ModelAndView cadastrar(ContatoEmail contatoEmail){
        ModelAndView modelAndView = new ModelAndView("redirect:/page/");
        contatoEmailServico.cadastrarContatoEmail(contatoEmail);
        return modelAndView;
    }
	
	@PostMapping("/agendar/{id}")
    public ModelAndView agendar(@PathVariable Long id, Principal principal, ViagemAgendada viagemAgendada) {
		ModelAndView modelAndView = new ModelAndView("redirect:/page/minhasViagens");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
		viagemAgendada.setDestino(destinoServico.buscarPorId(id));
		if(viagemAgendada.getDestino().isPromocao()){
			viagemAgendada.setPreco(viagemAgendada.getDestino().getPrecoPromocao());
		} else {
			viagemAgendada.setPreco(viagemAgendada.getDestino().getPreco());
		}
		viagemAgendada.setUsuario(usuarioDetailsService.buscarPorId(usuarioLogado.getId()));
		log.info(viagemAgendada);
        viagemAgendadaServico.cadastrarViagemAgendada(viagemAgendada);
        return modelAndView;
	}
	
	@PostMapping("/criarAlertaPreco/{id}")
    public ModelAndView criarAlertaPreco(@PathVariable Long id, Principal principal, AlertaPreco alertaPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/page/meusAlertas");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
		alertaPreco.setDestino(destinoServico.buscarPorId(id));
		alertaPreco.setUsuario(usuarioDetailsService.buscarPorId(usuarioLogado.getId()));
		log.info(alertaPreco);
		alertaPrecoServico.criarAlerta(alertaPreco);
        return modelAndView;
	}
	
	
	
	@GetMapping("/alerta/{id}/excluir")
    public ModelAndView excluirAlerta(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/page/meusAlertas");
        alertaPrecoServico.deletarAlerta(id);
        return modelAndView;
    }
	//--------------------------------------------------------
	//Utilitárias
	//--------------------------------------------------------
	
	//Carregar imagem do banco
	@GetMapping("/imagem/{id}")
    @ResponseBody
    public byte[] exibirImagen(@PathVariable("id") Long id) {
		Destino destino = this.destinoServico.buscarPorId(id);
        return destino.getImagem();
    }
	
	public ModelAndView adicionarDadosUsuarioView(Principal principal, ModelAndView modelAndView) {
		if(principal != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean isAdmin = authentication.getAuthorities().stream()
			          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
			
			modelAndView.addObject("isLogged", true);
			if(principal.getName() != null) {
				Usuario usuarioLogado = Usuario.class.cast(authentication.getPrincipal());
				modelAndView.addObject("usuarioNavbar", usuarioLogado);
				modelAndView.addObject("notificacoes", alertaPrecoServico.verificarAlerta(usuarioLogado));
			} else {
				modelAndView.addObject("usuarioNavbar", "usuario");
			}
			modelAndView.addObject("isAdmin", isAdmin);
		} else {
			modelAndView.addObject("usuarioNavbar", "usuario");
			modelAndView.addObject("isLogged", false);
		}
		return modelAndView;
	}
}
