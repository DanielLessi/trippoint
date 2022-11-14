package br.com.recode.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.recode.entidades.Destino;

public interface DestinoRepositorio extends JpaRepository<Destino, Long>{
	
	@Query(value = "SELECT * FROM Destino WHERE promocao = 1", nativeQuery = true)
	List<Destino> findByPromocao();
	
	@Modifying
	@Transactional
	@Query(value = "Update Destino set "
			+ "nome = :nome,"
			+ "pais = :pais,"
			+ "continente = :continente,"
			+ "preco = :preco,"
			+ "promocao = :promocao,"
			+ "porc_desconto = :porcDesconto,"
			+ "imagem = :imagem"
			+ " WHERE id = :id", nativeQuery = true)
	void atualizaUsuario(
			@Param("id")Long id,
			@Param("nome")String nome,
			@Param("pais")String pais,
			@Param("continente")String continente,
			@Param("preco")double preco,
			@Param("promocao")boolean promocao,
			@Param("porcDesconto")double porcDesconto,
			@Param("imagem")byte[] imagem
			);
	
	@Modifying
	@Transactional
	@Query(value = "Update Destino set "
			+ "nome = :nome,"
			+ "pais = :pais,"
			+ "continente = :continente,"
			+ "preco = :preco,"
			+ "promocao = :promocao,"
			+ "porc_desconto = :porcDesconto"
			+ " WHERE id = :id", nativeQuery = true)
	void atualizaUsuarioSemImagem(
			@Param("id")Long id,
			@Param("nome")String nome,
			@Param("pais")String pais,
			@Param("continente")String continente,
			@Param("preco")double preco,
			@Param("promocao")boolean promocao,
			@Param("porcDesconto")double porcDesconto
			);

}