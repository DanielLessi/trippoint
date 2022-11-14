package br.com.recode.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.recode.entidades.Usuario;
import br.com.recode.entidades.ViagemAgendada;

public interface ViagemAgendadaRepositorio extends JpaRepository<ViagemAgendada, Long> {

	List<ViagemAgendada> findAllByUsuario(Usuario usuario);
	
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO viagem_agendada ("
    		+ "preco, data_ida, hora_ida, data_volta, hora_volta, destino_id, usuario_id) "
        + "VALUES (:#{#v.preco}, :#{#v.dataIda}, :#{#v.horaIda}, :#{#v.dataVolta},"
        + " :#{#v.horaVolta}, :#{#v.destino}, :#{#v.usuario})", nativeQuery = true)
    public void reservar(@Param("v") ViagemAgendada viagemAgendada);
}
