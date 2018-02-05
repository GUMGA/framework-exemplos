package br.com.servicosCRUD.domain.model;
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
@Entity(name = "Time")
@Table(name = "Time", indexes = {
    @Index(name = "Time_gum_oi", columnList = "oi")
})
public class Time extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @Column(name = "nomeEstadio")
	private String nomeEstadio;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pessoa> jogadores;

    public Time() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeEstadio() {
		return this.nomeEstadio;
	}
	public void setNomeEstadio(String nomeEstadio) {
		this.nomeEstadio = nomeEstadio;
	}

	public List<Pessoa> getJogadores() {
		return this.jogadores;
	}
	public void setJogadores(List<Pessoa> jogadores) {
		this.jogadores = jogadores;
	}
}
