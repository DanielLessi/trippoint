package br.com.recode.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.recode.entidades.ContatoEmail;
import br.com.recode.repositorio.ContatoEmailRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContatoEmailServico{
	
	private final ContatoEmailRepositorio contatoEmailRepositorio;
	
	public List<ContatoEmail> listarTodosLista(){
		return contatoEmailRepositorio.findAll();
	}
	
	public List<ContatoEmail> listarTodosEmail(String email){
		return contatoEmailRepositorio.findByEmail(email);
	}

	@Transactional
	public ContatoEmail cadastrarContatoEmail(ContatoEmail contatoEmail) {	
		return contatoEmailRepositorio.save(contatoEmail);
	}
	
}