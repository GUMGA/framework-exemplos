package br.com.remocaoLogica.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import io.gumga.domain.domains.*;
import io.gumga.domain.logicaldelete.GumgaLDModel;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Pessoa")
@Table(name = "Pessoa", indexes = {
    @Index(name = "Pessoa_gum_oi", columnList = "oi")
})
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_PessoaId")
public class Pessoa extends GumgaLDModel<Long> {


    @Column(name = "nome")
	private String nome;
    @Column(name = "peso")
	private Double peso;
    @Column(name = "altura")
	private Double altura;

    public Pessoa() {}

	public Pessoa(String nome, Double peso, Double altura) {
		this.nome = nome;
		this.peso = peso;
		this.altura = altura;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPeso() {
		return this.peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return this.altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
}
