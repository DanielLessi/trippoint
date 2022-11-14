package br.com.recode.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.recode.entidades.Destino;
import br.com.recode.repositorio.DestinoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinoServico{
	
	private final DestinoRepositorio destinoRepositorio;
	
	public List<Destino> listarTodosLista(){
		return destinoRepositorio.findAll();
	}
	
	public Page<Destino> listarTodosPaginado(Pageable pageable){
		return destinoRepositorio.findAll(pageable);
	}
	
	public List<Destino> listarPromocao(){
		return destinoRepositorio.findByPromocao();
	}
	
	public Destino buscarPorId(long id){
		return destinoRepositorio.getOne(id);
	}
	
	@Transactional
	public Destino cadastrarDestino(Destino destino) {	
		return destinoRepositorio.save(destino);
	}
	
	public void deletarDestino(long id){
		destinoRepositorio.delete(buscarPorId(id));
	}
	
	public void atualizarDestino(Destino destino){
		destinoRepositorio.atualizaUsuario(
				destino.getId(),
				destino.getNome(),
				destino.getPais(),
				destino.getContinente(),
				destino.getPreco(),
				destino.isPromocao(),
				destino.getPorcDesconto(),
				destino.getImagem()
				);		
	}
	
	public void atualizarDestinoSemImagem(Destino destino){
		destinoRepositorio.atualizaUsuarioSemImagem(
				destino.getId(),
				destino.getNome(),
				destino.getPais(),
				destino.getContinente(),
				destino.getPreco(),
				destino.isPromocao(),
				destino.getPorcDesconto()
				);		
	}
	
}
