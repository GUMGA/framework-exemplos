package br.com.gLogService.domain.model;
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
@Entity(name = "Card")
@Table(name = "Card", indexes = {
    @Index(name = "Card_gum_oi", columnList = "oi")
})
public class Card extends GumgaModelUUID {


    @Column(name = "name")
	private String name;
    @Column(name = "number")
	private Long number;
    @Column(name = "validity")
	private String validity;
    @Column(name = "csv")
	private Integer csv;

    public Card() {}

	public Card(String name, Long number, String validity, Integer csv) {
		this.name = name;
		this.number = number;
		this.validity = validity;
		this.csv = csv;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getNumber() {
		return this.number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}

	public String getValidity() {
		return this.validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Integer getCsv() {
		return this.csv;
	}
	public void setCsv(Integer csv) {
		this.csv = csv;
	}
}
