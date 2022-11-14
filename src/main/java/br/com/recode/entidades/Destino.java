package br.com.recode.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
public class Destino {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String pais;
	@Column(nullable = false)
	private String continente;
	@Column(nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double preco;
	@Column(nullable = false)
	private boolean promocao;
	@NumberFormat(pattern = "#,##0.00")
	private double porcDesconto;
	@Lob
	private byte[] imagem;
	
	public double getPrecoPromocao() {
		if(!this.isPromocao()) {
			return this.preco;
		}
		return this.preco * (1-(this.porcDesconto/100));
	}
}
