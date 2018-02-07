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
@Entity(name = "Carrinho")
@Table(name = "Carrinho", indexes = {
    @Index(name = "Carrinho_gum_oi", columnList = "oi")
})
public class Carrinho extends GumgaModelUUID {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Produto> produtos = new ArrayList<>();
    @Column(name = "valorFrete")
	private Double valorFrete;
    @Column(name = "nomeUsuario")
	private String nomeUsuario;

    public Carrinho() {}

	public Carrinho(Double valorFrete, String nomeUsuario) {
		this.valorFrete = valorFrete;
		this.nomeUsuario = nomeUsuario;
	}

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

//    public List<Produto> getProdutos() {
//		return this.produtos;
//	}
//	public void setProdutos(List<Produto> produtos) {
//		this.produtos = produtos;
//	}

	public Double getValorFrete() {
		return this.valorFrete;
	}
	public void setValorFrete(Double valorFrete) {
		this.valorFrete = valorFrete;
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
}
