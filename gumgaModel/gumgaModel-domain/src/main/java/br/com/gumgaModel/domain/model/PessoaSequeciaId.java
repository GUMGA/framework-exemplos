package br.com.gumgaModel.domain.model;
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
@Entity(name = "PessoaSequeciaId")
@Table(name = "PessoaSequeciaId", indexes = {
    @Index(name = "PessoaSequeciaId_gum_oi", columnList = "oi")
})
/**
 * Quando estamos utilizando o GumgaModel<ID>, o id dos objetos instanciados serão sequenciais
 * Por isso precisamos configurar um gerador de sequência para que o hibernate possa utilizar
 * para isto, devemos adicionar a anotação @SequenceGenerator passando os parâmetros como o
 * exemplo a seguir
 */
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_PessoaId")
public class PessoaSequeciaId extends GumgaModel<Long> {

    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;
    @Column(name = "altura")
	private Double altura;
    @Column(name = "peso")
	private Double peso;

    public PessoaSequeciaId() {}

	public PessoaSequeciaId(String nome, Integer idade, Double altura, Double peso) {
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return this.idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Double getAltura() {
		return this.altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getPeso() {
		return this.peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
}
