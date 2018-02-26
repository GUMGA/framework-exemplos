package io.gumga.gquery.domain.model;
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
@Entity(name = "Pessoa")
@Table(name = "Pessoa", indexes = {
    @Index(name = "Pessoa_gum_oi", columnList = "oi")
})
public class Pessoa extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos;
    @Column(name = "idade")
	private int idade;

    public Pessoa() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Endereco> getEnderecos() {
		return this.enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public int getIdade() {return idade;}

	public void setIdade(int idade) {this.idade = idade;}
}
