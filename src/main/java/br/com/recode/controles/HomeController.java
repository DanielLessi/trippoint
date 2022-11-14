package br.com.recode.controles;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.recode.entidades.Usuario;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

	@GetMapping({"", "/", "/index"})
	public ModelAndView index(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("index.html");
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
	    return modelAndView;
	}
	
	@GetMapping("/login")
    public ModelAndView login(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("login.html");
		
		modelAndView = adicionarDadosUsuarioView(principal, modelAndView);
		
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
