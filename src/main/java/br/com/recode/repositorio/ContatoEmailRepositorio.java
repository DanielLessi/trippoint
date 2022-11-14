package br.com.recode.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.recode.entidades.ContatoEmail;

public interface ContatoEmailRepositorio extends JpaRepository<ContatoEmail, Long> {

	List<ContatoEmail> findByEmail(String email);
}
