package br.com.recode.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ViagemAgendada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double preco;
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate dataIda;
	private LocalTime horaIda;
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate dataVolta;
	private LocalTime horaVolta;
	@ManyToOne
    private Destino destino;
	@ManyToOne
    private Usuario usuario;
}