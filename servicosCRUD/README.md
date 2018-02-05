# Serviços CRUD
##### *Create, Read, Update and Delete*

O Framework Gumga fornece por padrão todas as funcionalidades básicas de um sistema, neste módulo iremos abordar as operações CRUD, a partir das interfaces e api's que suprem estas funções.
Como exemplo criamos um projeto a partir do Gumga Gerador com as entidades Pessoa e Time, segue a modelagem abaixo:

Classe Pessoa:
```java
package br.com.servicosCRUD.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "Pessoa")
@Table(name = "Pessoa", indexes = {
    @Index(name = "Pessoa_gum_oi", columnList = "oi")
})
public class Pessoa extends GumgaModelUUID {
    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;
    @Column(name = "peso")
	private Double peso;

    public Pessoa() {}

/**
* Getters and Setters
**/
}
```

Classe Time:
```java
package br.com.servicosCRUD.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "Time")
@Table(name = "Time", indexes = {
    @Index(name = "Time_gum_oi", columnList = "oi")
})
public class Time extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @Column(name = "nomeEstadio")
	private String nomeEstadio;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pessoa> jogadores;

    public Time() {}
/**
* Getters and Setters
**/
}
```
Antes de iniciarmos os exemplos práticos, vamos entender um pouco mais sobre as entidades envolvidas.
# Classes de Serviços (Gumga Service)
* #### AbstractGumgaService
    ##### Class AbstractGumgaService<T,ID extends java.io.Serializable>
    >Veja a documentação completa [clicando aqui](https://gumga.github.io/assets/java-docs/applicationdocs/index.html)

    Classe abstrata que contém métodos para criação de serviços para manipulação da classe domínio, id da organização e     busca de campos customizados
**Type Parameters:**
    * **T** - Classe que contenha um identificador padrão, exemplo: ID do registro
    * **ID** - Tipo do identificador contido na classe

* #### GumgaService
    ##### Class GumgaService<T extends GumgaIdable<ID>,ID extends java.io.Serializable>
    Classe abstrata que contém métodos para criação de serviços para manipulação de entidade (criação, alteração, deleção     e busca)
    >Esta classe extende de AbstractGumgaService

* #### GumgaNoDeleteService
    ##### Class GumgaNoDeleteService<T extends GumgaIdable<ID>,ID extends java.io.Serializable>
    Classe abstrata que contém métodos para criação de serviços para manipulação de entidade (criação, alteração e busca, porém sem exclusão)
    >Esta classe extende de AbstractGumgaService

* #### GumgaReadOnlyService
     ##### Class GumgaReadOnlyService<T extends GumgaIdable<?>,ID extends java.io.Serializable>

    Classe abstrata que contém métodos de serviços somente de leitura da entidade
   
    >Esta classe extende de AbstractGumgaService

# Classes de Acesso (API)
* #### AbstractProtoGumgaAPI

    >Veja a documentação completa [clicando aqui](https://gumga.github.io/assets/java-docs/presentationdocs/index.html)

    **Type Parameters:**
    * **T** - Classe de referência
    * **ID** - Tipo do identificador contido na classe

    ##### Class AbstractProtoGumgaAPI<T,ID extends java.io.Serializable>

    Classe abstrata gerérica que contém métodos para a validação de campos obrigatórios
    
* #### AbstractGumgaAPI

    ##### Class AbstractGumgaAPI<T,ID extends java.io.Serializable>

    API para acesso aos métodos de serviços que o Framework dispõe via requisições REST
    
    

----
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
