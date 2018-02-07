package br.com.seeds.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import io.gumga.domain.domains.*;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Produto")
@Table(name = "Produto", indexes = {
    @Index(name = "Produto_gum_oi", columnList = "oi")
})
public class Produto extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @Column(name = "valor")
	private Double valor;
    @Column(name = "peso")
	private Double peso;

    public Produto() {}

	/**
	 * Criamos um construtor para facilitar na hora de criar as instância do seed
	 * @param nome String com o nome do produto
	 * @param valor double com o valor sugerido do produto
	 * @param peso double com o peso para cálculo do frete
	 */
	public Produto(String nome, Double valor, Double peso) {
		this.nome = nome;
		this.valor = valor;
		this.peso = peso;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return this.valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getPeso() {
		return this.peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
}
