package br.com.recode.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class AlertaPreco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double preco;
	private boolean valorAbaixoAlerta;
	private boolean usuarioClicou;
	@ManyToOne
    private Destino destino;
	@ManyToOne
    private Usuario usuario;
}
