# Gumga Model
---

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

  As entidades Model são de suma importância no Framework Gumga, são elas que modelam a estrutura básica de todas as outras classes do domínio da aplicação. <br>
  Ou seja, as entidades de domínio sempre vão extender de alguma delas.

  São ao todo quatro classes de modelo cada uma com especifidades que vamos abordar logo mais:

  * *GumgaModelUUID*
  * *GumgaModel*
  * *GumgaSharedModelUUID*
  * *GumgaSharedModel*

A principal diferênça entre cada uma delas é a forma de geração do id do objeto instanciado e a forma de manipulação do oi (gerenciamento de organizações)<br>

  #### GumgaModelUUID
  Neste modelo o id gerado é um valor aleatório de 256 bits (32 caracteres alfanuméricos)<br>
  > Consulte a classe UUIDUtil.java para mais informações sobre os critérios utilizados para a geração destes valores

  ```java
  package io.gumga.domain;
//imports
  @MappedSuperclass
  public abstract class GumgaModelUUID implements GumgaIdable<String>, Serializable {
      @Id
      @Column(name = "id")
      protected String id;
      @Column(name = "oi")
      protected GumgaOi oi;

      public GumgaModelUUID() {
          Class classe = this.getClass();
          if (classe.isAnnotationPresent(GumgaMultitenancy.class) && oi == null) {
              String oc = GumgaThreadScope.organizationCode.get();
              if (oc == null) {
                  GumgaMultitenancy tenancy = this.getClass().getAnnotation(GumgaMultitenancy.class);
                  oc = tenancy.publicMarking().getMark();
              }
              oi = new GumgaOi(oc);
          }
          this.id = UUIDUtil.generate();
      }
      @Override
      public int hashCode() {
          int hash = 7;
          if (id == null) {
              return super.hashCode();
          }
          hash = 29 * hash + Objects.hashCode(this.id);
          return hash;
      }
      @Override
      public boolean equals(Object obj) {
          if (obj == null) {
              return false;
          }
          if (getClass() != obj.getClass()) {
              return false;
          }
          final GumgaModelUUID other = (GumgaModelUUID) obj;

          if (!Objects.equals(this.id, other.id)) {
              return false;
          }
          return true;
      }
      //Getters ans Setters
  }

  ```
No nosso exemplo, geramos uma classe com alguns atributos
> Quando geramos um projeto pelo Gumga Gerador, por padrão todas as classes são declaradas extendendo da GumgaModelUUID


```java
package br.com.gumgaModel.domain.model;
//imports

@GumgaMultitenancy
@Audited
@Entity(name = "PessoaAleatorioId")
@Table(name = "PessoaAleatorioId", indexes = {
    @Index(name = "PessoaAleatorioId_gum_oi", columnList = "oi")
})
public class PessoaAleatorioId extends GumgaModelUUID {

    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;
    @Column(name = "altura")
	private Double altura;
    @Column(name = "peso")
	private Double peso;

    public PessoaAleatorioId() {}

	public PessoaAleatorioId(String nome, Integer idade, Double altura, Double peso) {
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
	}
//Getters and Setters
}

```
Desta maneira, quando instanciamos um objeto desta entidade temos o seguinte:
> Implementamos um seed para gerar estas instâncias no banco de dados, para mais informações sobre como trabalhar com seeds consulte o topico "Seeds" no repositório

```json
{
    "pageSize": 10,
    "count": 7,
    "start": 0,
    "values": [
        {
            "id": "35EF24186E2EC0DCC8AE02426DFA91B7",
            "oi": null,
            "nome": "Felipe S.",
            "idade": 25,
            "altura": 1.72,
            "peso": 108.5
        },
        {
            "id": "35EF24186E55D0579DE502426DFA91B7",
            "oi": null,
            "nome": "Mateus M.",
            "idade": 21,
            "altura": 1.52,
            "peso": 95
        },
        {
            "id": "35EF24186E55D1BE011402426DFA91B7",
            "oi": null,
            "nome": "Caio M.",
            "idade": 25,
            "altura": 1.61,
            "peso": 98.6
        },
        {
            "id": "35EF24186E55D26FD8ED02426DFA91B7",
            "oi": null,
            "nome": "Gabi P.",
            "idade": 18,
            "altura": 1.59,
            "peso": 52.3
        },
        {
            "id": "35EF24186E55D331420502426DFA91B7",
            "oi": null,
            "nome": "William D.",
            "idade": 26,
            "altura": 1.82,
            "peso": 78.7
        },
        {
            "id": "35EF24186E55D49DF52802426DFA91B7",
            "oi": null,
            "nome": "Lusca K.",
            "idade": 22,
            "altura": 1.75,
            "peso": 49.8
        },
        {
            "id": "35EF24186E55D57BFEC902426DFA91B7",
            "oi": null,
            "nome": "Luiz P.",
            "idade": 45,
            "altura": 1.79,
            "peso": 85
        }
    ]
}
```
> Você pode verificar os objetos criados diretamente no banco de dados, que neste exemplo está rodando o H2 em memória, ou pela API do servidor <br>
No caso fizemos uma requisição GET na rota
 **\*/gumgaModel-api/api/pessoaaleatorioid** <br>
 \* O banco de dados utilizado neste exemplo é o H2 rodando em memória\*

Veja que os valores de id dos objetos não seguem nenhuma sequência lógica

  #### GumgaModel
  Neste modelo os id's dos objetos instanciados seguem uma sequência numérica começando em 1, seguindo a ordem de inserção no banco de dados
```java
package io.gumga.domain;

//imports

@MappedSuperclass
@TypeDefs({
    @TypeDef(name = "gumgaaddress", defaultForType = GumgaAddress.class, typeClass = GumgaAddressUserType.class),
    @TypeDef(name = "gumgaboolean", defaultForType = GumgaBoolean.class, typeClass = GumgaBooleanUserType.class),
    @TypeDef(name = "gumgabarcode", defaultForType = GumgaBarCode.class, typeClass = GumgaBarCodeUserType.class),
    @TypeDef(name = "gumgacep", defaultForType = GumgaCEP.class, typeClass = GumgaCEPUserType.class),
    @TypeDef(name = "gumgacnpj", defaultForType = GumgaCNPJ.class, typeClass = GumgaCNPJUserType.class),
    @TypeDef(name = "gumgacpf", defaultForType = GumgaCPF.class, typeClass = GumgaCPFUserType.class),
    @TypeDef(name = "gumgaemail", defaultForType = GumgaEMail.class, typeClass = GumgaEMailUserType.class),
    @TypeDef(name = "gumgafile", defaultForType = GumgaFile.class, typeClass = GumgaFileUserType.class),
    @TypeDef(name = "gumgageolocation", defaultForType = GumgaGeoLocation.class, typeClass = GumgaGeoLocationUserType.class),
    @TypeDef(name = "gumgaip4", defaultForType = GumgaIP4.class, typeClass = GumgaIP4UserType.class),
    @TypeDef(name = "gumgaip6", defaultForType = GumgaIP6.class, typeClass = GumgaIP6UserType.class),
    @TypeDef(name = "gumgaimage", defaultForType = GumgaImage.class, typeClass = GumgaImageUserType.class),
    @TypeDef(name = "gumgamoney", defaultForType = GumgaMoney.class, typeClass = GumgaMoneyUserType.class),
    @TypeDef(name = "gumgamutilinestring", defaultForType = GumgaMultiLineString.class, typeClass = GumgaMultiLineStringUserType.class),
    @TypeDef(name = "gumgaphonenumber", defaultForType = GumgaPhoneNumber.class, typeClass = GumgaPhoneNumberUserType.class),
    @TypeDef(name = "gumgatime", defaultForType = GumgaTime.class, typeClass = GumgaTimeUserType.class),
    @TypeDef(name = "gumgaoi", defaultForType = GumgaOi.class, typeClass = GumgaOiUserType.class),
    @TypeDef(name = "gumgaurl", defaultForType = GumgaURL.class, typeClass = GumgaURLUserType.class)
})
public abstract class GumgaModel<ID extends Serializable> implements GumgaIdable<ID>, Serializable {

    public static final String SEQ_NAME = "SEQ";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = SEQ_NAME)
    protected ID id;
    protected GumgaOi oi;

    public GumgaModel() {
        Class classe = this.getClass();
        if (classe.isAnnotationPresent(GumgaMultitenancy.class)) {
            String oc = GumgaThreadScope.organizationCode.get();
            if (oc == null) {
                GumgaMultitenancy tenancy = this.getClass().getAnnotation(GumgaMultitenancy.class);
                oc = tenancy.publicMarking().getMark();
            }
            oi = new GumgaOi(oc);
        }
    }

    public GumgaModel(GumgaOi oi) {
        this.oi = oi;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        if (id == null) {
            return super.hashCode();
        }
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GumgaModel<?> other = (GumgaModel<?>) obj;

        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}

```
O resultado que temos ao gravar instâncias baseadas nesse modelo é este:
```json
{
    "pageSize": 10,
    "count": 7,
    "start": 0,
    "values": [
        {
            "id": 1,
            "oi": null,
            "nome": "Felipe S.",
            "idade": 25,
            "altura": 1.72,
            "peso": 108.5
        },
        {
            "id": 2,
            "oi": null,
            "nome": "Mateus M.",
            "idade": 21,
            "altura": 1.52,
            "peso": 95
        },
        {
            "id": 3,
            "oi": null,
            "nome": "Caio M.",
            "idade": 25,
            "altura": 1.61,
            "peso": 98.6
        },
        {
            "id": 4,
            "oi": null,
            "nome": "Gabi P.",
            "idade": 18,
            "altura": 1.59,
            "peso": 52.3
        },
        {
            "id": 5,
            "oi": null,
            "nome": "William D.",
            "idade": 26,
            "altura": 1.82,
            "peso": 78.7
        },
        {
            "id": 6,
            "oi": null,
            "nome": "Lusca K.",
            "idade": 22,
            "altura": 1.75,
            "peso": 49.8
        },
        {
            "id": 7,
            "oi": null,
            "nome": "Luiz P.",
            "idade": 45,
            "altura": 1.79,
            "peso": 85
        }
    ]
}
```
Observe que, o id começa em 1 e segue crescendo de acordo com a ordem de inserção<br>
Para que esta numeração fosse gerada e auto incrementada, tivemos que fazer algumas alterações na nossa classe domínio, veja abaixo:
```java
package br.com.gumgaModel.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "PessoaSequeciaId")
@Table(name = "PessoaSequeciaId", indexes = {
    @Index(name = "PessoaSequeciaId_gum_oi", columnList = "oi")
})

@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_PessoaId")
public class PessoaSequeciaId extends GumgaModel<Long> {

    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;
    @Column(name = "altura")
	private Double altura;
    @Column(name = "peso")
	private Double peso;

    public PessoaSequeciaId() {}

	public PessoaSequeciaId(String nome, Integer idade, Double altura, Double peso) {
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
	}
//Getters and Setters
}
```
Observe que, adicionamos a anotação @SequenceGenerator passando um nome para a sequência desejada, isto é para que o hibernate possa manipular corretamente a sequência dos objetos

Outro detalhe importante, é que no GumgaModel devemos informar o tipo do id que vamos manipular, que deve ser Long ( GumgaModel < Long > )

> Se você gerou seus domínios pelo gerador, será necessário adequar este tipo de objeto nas entidades de Serviço, API etc...

  #### GumgaSharedModelUUID e GumgaSharedModel

  Estas duas entidades seguem o mesmo padrão das vistas anteriormente (geração de id aleatório e sequencial), porém estas tem o atribulo de compartilhamento.
  Elas adicionam a possibilidade de manipulação de usuário e organização do objeto.
  Isso é útil quando trabalhamos com objetos que são compartilhados entre usuários e organizações distintas.

Os modos de geração de id seguem os mesmos de GumgaModelUUID (id aleatório) e GumgaModel(id sequencial)
Os campos adicionais são estes:
```java
    /**
     * Organizações
     */
    @Column(name = "gumga_orgs",length = MAX_LENGTH)
    private String gumgaOrganizations;
    /**
     * Usuários
     */
    @Column(name = "gumga_users",length = MAX_LENGTH)
    private String gumgaUsers;
```

Os registros resultantes da classe *GumgaSharedModel* são estes:
```json
       {
           "id": 1,
           "oi": null,
           "gumgaOrganizations": ",",
           "gumgaUsers": ",",
           "nome": "Felipe S.",
           "idade": 25,
           "altura": 1.72,
           "peso": 108.5
       },
       {
           "id": 2,
           "oi": null,
           "gumgaOrganizations": ",",
           "gumgaUsers": ",",
           "nome": "Mateus M.",
           "idade": 21,
           "altura": 1.52,
           "peso": 95
       },
```
E da classe *GumgaSharedModelUUID*:
```json
        {
            "id": "35EFCBD1C605806069C702426DFA91B7",
            "oi": null,
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Felipe S.",
            "idade": 25,
            "altura": 1.72,
            "peso": 108.5
        },
        {
            "id": "35EFCBD1C605817675F202426DFA91B7",
            "oi": null,
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Mateus M.",
            "idade": 21,
            "altura": 1.52,
            "peso": 95
        },
```
---
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
