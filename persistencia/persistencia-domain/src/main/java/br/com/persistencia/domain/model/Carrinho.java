package br.com.persistencia.domain.model;
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
@Entity(name = "Carrinho")
@Table(name = "Carrinho", indexes = {
    @Index(name = "Carrinho_gum_oi", columnList = "oi")
})
public class Carrinho extends GumgaModelUUID {

	@OneToMany
	private List<ItemCarrinho> itens = new ArrayList<>();


	@Column(name = "nomeUsuario")

	private String nomeUsuario;
    @Column(name = "fatorFrete")
	private Double fatorFrete;

    public Carrinho() {}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Double getFatorFrete() {
		return this.fatorFrete;
	}
	public void setFatorFrete(Double fatorFrete) {
		this.fatorFrete = fatorFrete;
	}

	public List<ItemCarrinho> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinho> itens) {
		this.itens = itens;
	}
}
