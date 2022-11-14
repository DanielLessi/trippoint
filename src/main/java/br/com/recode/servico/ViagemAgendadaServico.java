package br.com.recode.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.recode.entidades.Usuario;
import br.com.recode.entidades.ViagemAgendada;
import br.com.recode.repositorio.ViagemAgendadaRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ViagemAgendadaServico{
	
	private final ViagemAgendadaRepositorio viagemAgendadaRepositorio;
	
	public List<ViagemAgendada> listarTodosLista(){
		return viagemAgendadaRepositorio.findAll();
	}
	
	public List<ViagemAgendada> listarTodosUsuario(Usuario usuario){
		return viagemAgendadaRepositorio.findAllByUsuario(usuario);
	}
	
	@Transactional
	public void cadastrarViagemAgendada(ViagemAgendada viagemAgendada) {	
		log.info("Viagem Agendada Servico = " + viagemAgendada);
		viagemAgendadaRepositorio.reservar(viagemAgendada);
	}
	
}