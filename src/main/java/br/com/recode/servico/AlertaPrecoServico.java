package br.com.recode.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.recode.entidades.AlertaPreco;
import br.com.recode.entidades.Destino;
import br.com.recode.entidades.Usuario;
import br.com.recode.repositorio.AlertaPrecoRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AlertaPrecoServico{
	
	private final AlertaPrecoRepositorio alertaPrecoRepositorio;
	
	public List<AlertaPreco> listarTodosLista(){
		return alertaPrecoRepositorio.findAll();
	}
	
	public List<AlertaPreco> listarTodosUsuario(Usuario usuario){
		return alertaPrecoRepositorio.findAllByUsuario(usuario);
	}
	
	public void enviarAlerta(Destino destino) {
		alertaPrecoRepositorio.enviarAlerta(destino);
	}
	
	@Transactional
	public void criarAlerta(AlertaPreco alertaPreco) {
		alertaPrecoRepositorio.criarAlerta(alertaPreco);
	}
	
	@Transactional
	public void deletarAlerta(Long id) {
		alertaPrecoRepositorio.deleteById(id);
	}
	
	public int verificarAlerta(Usuario usuario) {
		log.info("Alertas do usuario = " + alertaPrecoRepositorio.verificarAlerta(usuario));
		return alertaPrecoRepositorio.verificarAlerta(usuario);
	}
	
	public AlertaPreco removerNotificacao(long id) {
		alertaPrecoRepositorio.removerNotificacao(id);
		return alertaPrecoRepositorio.getOne(id);
	}
}