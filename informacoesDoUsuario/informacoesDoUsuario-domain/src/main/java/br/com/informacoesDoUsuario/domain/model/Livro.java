package br.com.informacoesDoUsuario.domain.model;
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
@Entity(name = "Livro")
@Table(name = "Livro", indexes = {
    @Index(name = "Livro_gum_oi", columnList = "oi")
})
public class Livro extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @Column(name = "autor")
	private String autor;
    @Column(name = "anoPublicacao")
	private Integer anoPublicacao;

    public Livro() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return this.autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getAnoPublicacao() {
		return this.anoPublicacao;
	}
	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}
}
