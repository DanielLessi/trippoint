package br.com.recode.controles;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Log4j2
public class AdminNavigationControle {

	private final DestinoServico destinoServico;
	private final UsuarioDetailsService usuarioDetailsService;
	private final ContatoEmailServico contatoEmailServico;
	private final ViagemAgendadaServico viagemAgendadaServico;
	private final AlertaPrecoServico alertaPrecoServico;

	@GetMapping({ "/", "/admin/", "/admin", })
	public String redirect() {
		return "redirect:/admin/listarDestinos";
	}

	@GetMapping({"/listarDestinos"})
	public ModelAndView listarDestinos(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/listarDestinos.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		List<Destino> destinos = destinoServico.listarTodosLista();
		modelAndView.addObject("destinos", destinos);
		return modelAndView;
	}

	@GetMapping({ "/listarUsuarios" })
	public ModelAndView listarUsuarios(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/listarUsuarios.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		List<Usuario> usuarios = usuarioDetailsService.listarTodosLista();
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@GetMapping({ "/listarViagensAgendadas" })
	public ModelAndView listarViagensAgendadas(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/listarViagensAgendadas.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		List<ViagemAgendada> viagensAgendadas = viagemAgendadaServico.listarTodosLista();
		modelAndView.addObject("viagensAgendadas", viagensAgendadas);
		return modelAndView;
	}
	
	@GetMapping({ "/listarContatoEmail" })
	public ModelAndView listarContatoEmail(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/listarContatoEmail.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		List<ContatoEmail> contatosEmail = contatoEmailServico.listarTodosLista();
		modelAndView.addObject("contatosEmail", contatosEmail);
		return modelAndView;
	}

	@GetMapping("/cadastro")
	public ModelAndView cadastrar(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/cadastroDestino");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		modelAndView.addObject("destino", new Destino());
		return modelAndView;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(Destino destino, @RequestParam("imagemDestino") MultipartFile file)
			throws IOException {
		try {
			destino.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/");
		destinoServico.cadastrarDestino(destino);
		return modelAndView;
	}

	@GetMapping("/destino/{id}")
	public ModelAndView detalhar(@PathVariable Long id, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("admin/detalhar.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		Destino destino = destinoServico.buscarPorId(id);
		modelAndView.addObject("destino", destino);
		return modelAndView;
	}

	@PostMapping("/alterar")
	public ModelAndView editar(Destino destino, @RequestParam("imagemDestino") MultipartFile file) throws IOException {
		try {
			if (!file.isEmpty()) {
				destino.setImagem(file.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Destino.nome = " + destino.getImagem());
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/");
		if (file.isEmpty()) {
			log.info("Não enviou imagem, manter a atual");
			destinoServico.atualizarDestinoSemImagem(destino);
		} else {
			log.info("enviou imagem, atualizar");
			destinoServico.atualizarDestino(destino);
		}
		alertaPrecoServico.enviarAlerta(destino);
		return modelAndView;
	}

	@GetMapping("/{id}/excluir")
	public ModelAndView excluir(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/");
		destinoServico.deletarDestino(id);
		return modelAndView;
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
				log.info(usuarioLogado);
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
