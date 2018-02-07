package br.com.seeds.domain.model;
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
@Entity(name = "ItemCarrinho")
@Table(name = "ItemCarrinho", indexes = {
    @Index(name = "ItemCarrinho_gum_oi", columnList = "oi")
})
public class ItemCarrinho extends GumgaModelUUID {


    @ManyToOne
	private Produto produto;
    @Column(name = "quantidade")
	private Integer quantidade;

    public ItemCarrinho() {}

	public ItemCarrinho(Produto produto) {
    	this.quantidade = 1;
		this.produto = produto;
	}

	public Produto getProduto() {
		return this.produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
