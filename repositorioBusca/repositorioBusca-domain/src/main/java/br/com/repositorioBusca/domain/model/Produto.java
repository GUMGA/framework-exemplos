package br.com.repositorioBusca.domain.model;
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
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_ProdutoId")
public class Produto extends GumgaModel<Long> {


    @Column(name = "nome")
	private String nome;
    @Column(name = "precoCusto")
	private Double precoCusto;
    @Column(name = "precoVenda")
	private Double precoVenda;
    @Column(name = "margemLucro")
	private Integer margemLucro;
    @Column(name = "peso")
	private Double peso;
    @Column(name = "fatorFrete")
	private Double fatorFrete;
    @Column(name = "valorFrete")
	private Double valorFrete;

    public Produto() {}

	public Produto(String nome, Double precoCusto, Integer margemLucro, Double peso, Double fatorFrete) {
		this.nome = nome;
		this.precoCusto = precoCusto;
		this.margemLucro = margemLucro;
		this.peso = peso;
		this.fatorFrete = fatorFrete;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrecoCusto() {
		return this.precoCusto;
	}
	public void setPrecoCusto(Double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public Double getPrecoVenda() {
		return this.precoVenda;
	}

	public Integer getMargemLucro() {
		return this.margemLucro;
	}
	public void setMargemLucro(Integer margemLucro) {
		this.margemLucro = margemLucro;
	}

	public Double getPeso() {
		return this.peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getFatorFrete() {
		return this.fatorFrete;
	}
	public void setFatorFrete(Double fatorFrete) {
		this.fatorFrete = fatorFrete;
	}

	public Double getValorFrete() {
		return this.valorFrete;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public void setValorFrete(Double valorFrete) {
		this.valorFrete = valorFrete;
	}
}
