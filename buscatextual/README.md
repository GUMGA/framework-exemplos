# Busca textual (Full text)

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Full text refere-se a técnicas para busca textual no banco de dados em uma coleção de tabelas em determinado campos. Para que possa ser utilizado este mecanismo de busca, deve-se pré definir alguns pontos:

  - As entidades a ser pesquisado o texto
  - Os campos
  - O texto

### O que utilizar

Atualmente existe um serviço e um recurso para utilização do Full Text, os mesmos estão descritos abaixo:

* [MultiSearchApi] (Rota: /api/multisearch) -  API para acesso e busca de atributos diversos
    * Sub-Rota: search/{text} - Faz uma busca nos atributos anotados
        * @param text String com o conteúdo a ser pesquisado
        * @return Lista com os resultados de atributos compatíveis ao texto de entrada
* [GumgaUntypedRepository] - Serviço para busca full text
    * Método: fullTextSearchaaa - Busca texto na entidade nos atributos
    * Método: fullTextSearch - Fazer a pesquisa com os atributos que estão anotados com {@link org.hibernate.search.annotations.Field}
    * Método: getTodosAtributos - Todos os atributos de uma classe
    * Método: getAllIndexedEntities - Todas as entidades que estão anotadas com {@link Indexed}


### Exemplo

Para mapear a entidade para pesquisa Full text, você deve utilizar duas anotações. Para indexar a entidade nas buscas deve ser utilizado @Indexed e para selecionar quais atributos devem ser pesquisados, é necessário utilizar @Field.

```java
@GumgaMultitenancy
@Audited
@Entity(name = "Pessoa")
@Table(name = "Pessoa", indexes = {
    @Index(name = "Pessoa_gum_oi", columnList = "oi")
})
@Indexed //Indexação da entidade
public class Pessoa extends GumgaModelUUID {

	@Field   // Seleção de atributo a ser pesquisado
    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;

    public Pessoa() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return this.idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
}

```

Para utilização do serviço, você pode injetar o mesmo da seguinte maneira:

```java
@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, String> {
     @Autowired
     private GumgaUntypedRepository gumgaUntypedRepository;
```

Nas rotas disponíveis para buscas, você pode fazer as seguintes requisições como exemplo::

```curl
 GET http://minha-api.com.br/api/multisearch/search/textoapesquisar
```

### Observações importantes

Quando uma classe é anotada com @Indexed, o hibernate gera automaticamente alguns arquivos utilizados na indexação das entidades.


See [Documentação Application](https://gumga.github.io/assets/java-docs/applicationdocs/index.html)
See [Documentação Presentation](https://gumga.github.io/assets/java-docs/presentationdocs/index.html)

License
----

GPL-3.0


**Free Software, Hell Yeah!**
