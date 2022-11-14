package br.com.recode.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.recode.entidades.AlertaPreco;
import br.com.recode.entidades.Destino;
import br.com.recode.entidades.Usuario;

public interface AlertaPrecoRepositorio extends JpaRepository<AlertaPreco, Long> {

	List<AlertaPreco> findAllByUsuario(Usuario usuario);
	
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO alerta_preco ("
    		+ "preco, valor_abaixo_alerta, usuario_clicou, destino_id, usuario_id) "
        + "VALUES (:#{#a.preco}, :#{#a.valorAbaixoAlerta}, :#{#a.usuarioClicou},"
        + " :#{#a.destino}, :#{#a.usuario})", nativeQuery = true)
    public void criarAlerta(@Param("a") AlertaPreco alertaPreco);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update alerta_preco set valor_abaixo_alerta = 1 AND usuario_clicou = 0"
			+ " WHERE destino_id = :#{#d.id} AND preco <= :#{#d.preco}", nativeQuery = true)
    public void enviarAlerta(@Param("d") Destino destino);
    
    @Query(value = "SELECT COUNT(*)FROM alerta_preco WHERE "
			+ "usuario_id = :#{#u.id} AND valor_abaixo_alerta = 1 AND usuario_clicou = 0", nativeQuery = true)
    public int verificarAlerta(@Param("u") Usuario usuario);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update alerta_preco set usuario_clicou = 1"
			+ " WHERE id = :id", nativeQuery = true)
    public void removerNotificacao(@Param("id")Long id);
}