package br.com.gumgaNLP.domain.model;

import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;

import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

import io.gumga.domain.domains.*;
import io.gumga.domain.nlp.GumgaNLPThing;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Produto")
@Table(name = "Produto", indexes = {
        @Index(name = "Produto_gum_oi", columnList = "oi")
})
@GumgaNLPThing
public class Produto extends GumgaModelUUID {

    @GumgaNLPThing("nome")
    @Column(name = "nome")
    private String nome;

    @GumgaNLPThing("custo")
    @Column(name = "custo")
    private Integer custo;

    @GumgaNLPThing("venda")
    @Column(name = "venda")
    private Integer venda;

    @GumgaNLPThing("peso")
    @Column(name = "peso")
    private Integer peso;

    @GumgaNLPThing("categoria")
    @Column(name = "categoria")
    private String categoria;

    public Produto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getcusto() {
        return custo;
    }

    public void setcusto(Integer custo) {
        this.custo = custo;
    }

    public Integer getVenda() {
        return venda;
    }

    public void setVenda(Integer venda) {
        this.venda = venda;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto { Nome: " + nome + ", Valor de Custo: " + custo + ", Valor de Venda: " + venda + ", Categoria: " + categoria + ", Peso: " + peso + "}";
    }
}
