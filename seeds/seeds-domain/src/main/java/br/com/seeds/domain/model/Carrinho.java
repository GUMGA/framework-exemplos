package br.com.seeds.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

import io.gumga.domain.GumgaSharedModelUUID;
import io.gumga.domain.domains.*;
import io.gumga.domain.shared.GumgaSharedModel;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Carrinho")
@Table(name = "Carrinho", indexes = {
    @Index(name = "Carrinho_gum_oi", columnList = "oi")
})
public class Carrinho extends GumgaSharedModelUUID {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();
    @Column(name = "valorFrete")
	private Double valorFrete;
    @Column(name = "nomeUsuario")
	private String nomeUsuario;

    public Carrinho() {}

    /**
     * Construtor para facilitar na hora de instanciar os objetos
     * @param valorFrete Fator multiplicativo para cálculo de frete
     * @param nomeUsuario Nome do usuário correspondente ao carrinho
     */
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
