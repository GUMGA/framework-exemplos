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
@Entity(name = "Endereco")
@Table(name = "Endereco", indexes = {
    @Index(name = "Endereco_gum_oi", columnList = "oi")
})
public class Endereco extends GumgaModelUUID {


    @Column(name = "logradouro")
	private String logradouro;
    @Column(name = "numero")
	private Integer numero;

    public Endereco() {}

	public String getLogradouro() {
		return this.logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return this.numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
