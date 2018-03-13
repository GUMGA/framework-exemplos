package br.com.customFields.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

import io.gumga.domain.customfields.GumgaCustomizableModel;
import io.gumga.domain.domains.*;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Conta")
@Table(name = "Conta", indexes = {
    @Index(name = "Conta_gum_oi", columnList = "oi")
})
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_ContaId")
public class Conta extends GumgaCustomizableModel<Long> {


    @Column(name = "agencia")
	private String agencia;
    @Column(name = "numero")
	private Integer numero;
    @Column(name = "nome")
	private String nome;

    public Conta() {}

	public String getAgencia() {
		return this.agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Integer getNumero() {
		return this.numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
