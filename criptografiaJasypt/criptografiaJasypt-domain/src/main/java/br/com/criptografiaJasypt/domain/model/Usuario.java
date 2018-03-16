package br.com.criptografiaJasypt.domain.model;
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
@Entity(name = "Usuario")
@Table(name = "Usuario", indexes = {
    @Index(name = "Usuario_gum_oi", columnList = "oi")
})
public class Usuario extends GumgaModelUUID {


    @Column(name = "login", unique = true)
	private String login;
    @Column(name = "senha")
	private String senha;
    @Column(name = "nome")
	private String nome;
    @Column(name = "dataNasc")
	private Date dataNasc;
    @Columns(columns = {
    @Column(name = "endereco_zip_code"),
    @Column(name = "endereco_premisse_type"),
    @Column(name = "endereco_premisse"),
    @Column(name = "endereco_number"),
    @Column(name = "endereco_information"),
    @Column(name = "endereco_neighbourhood"),
    @Column(name = "endereco_localization"),
    @Column(name = "endereco_state"),
    @Column(name = "endereco_country"),
    @Column(name = "endereco_latitude"),
    @Column(name = "endereco_longitude"),
    @Column(name = "endereco_formal_code"),
    @Column(name = "endereco_state_code")
    })
	private GumgaAddress endereco;

    public Usuario() {}

	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}
	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public GumgaAddress getEndereco() {
		return this.endereco;
	}
	public void setEndereco(GumgaAddress endereco) {
		this.endereco = endereco;
	}
}







