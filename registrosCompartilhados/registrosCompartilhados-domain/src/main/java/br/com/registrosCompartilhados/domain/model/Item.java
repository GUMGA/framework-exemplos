package br.com.registrosCompartilhados.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import io.gumga.domain.domains.*;
import io.gumga.domain.shared.GumgaSharedModel;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Item")
@Table(name = "Item", indexes = {
    @Index(name = "Item_gum_oi", columnList = "oi")
})
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_ItemId")
public class Item extends GumgaSharedModel<Long> {


    @Column(name = "nome")
	private String nome;
    @Column(name = "desc")
	private String desc;
    @Column(name = "preco")
	private BigDecimal preco;
	@Column(name = "categoria")
	@Enumerated(EnumType.STRING)
	private Categorias categoria;

    public Item() {}

	public Item(String nome, String desc, BigDecimal preco, Categorias categoria) {
		this.nome = nome;
		this.desc = desc;
		this.preco = preco;
		this.categoria = categoria;
	}

	public Categorias getCategoria() {
		return categoria;
	}

	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}
