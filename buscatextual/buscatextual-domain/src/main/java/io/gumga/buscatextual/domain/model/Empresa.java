package io.gumga.buscatextual.domain.model;

import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@GumgaMultitenancy
@Audited
@Entity(name = "Empresa")
@Table(name = "Empresa", indexes = {
    @Index(name = "Empresa_gum_oi", columnList = "oi")
})
@Indexed
public class Empresa extends GumgaModelUUID {

	@Field
    @Column(name = "nome")
	private String nome;
    @Column(name = "endereco")
	private String endereco;
    @Column(name = "inscEstadual")
	private Integer inscEstadual;

    public Empresa() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return this.endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getInscEstadual() {
		return this.inscEstadual;
	}
	public void setInscEstadual(Integer inscEstadual) {
		this.inscEstadual = inscEstadual;
	}
}
