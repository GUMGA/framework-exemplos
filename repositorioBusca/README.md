# Repositório de Busca


[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)



A arquitetura do Gumga Framework prevê uma interface *repository* para cada entidade do domínio que é persistida.
>Para saber mais sobre os papéis de cada serviço do Framework acesse a documentação no tópico Gerador GG.

Esta interface é responsável por concentrar os métodos de acesso a dados do sistema, e por sua vez, esta deve herdar os métodos e parâmetros da tecnologia adotada.<br>
No exemplo abaixo temos uma interface repository da entidade *Pessoa*:
```java
@Repository
public interface PessoaRepositorySpring extends GumgaCrudRepository<Pessoa, Long> {
//methods
}
  ```
#### GumgaCrudRepository e Spring Data JPA




Neste exemplo vamos abordar os mecanismos de buscas de registros no banco de dados. <br>
O principal motor de acesso a dados que o Gumga Framework utiliza é o **Spring Data JPA**, este é implementado por padrão nas interfaces de Repositório do domínio (quando iniciamos um projeto pelo cli *Gumga GG*).

>Neste projeto criamos alguns Seeds para podermos fazer algumas buscas de registros de pessoas e produtos.

Como pode ser observado, nossa interface repositório extende de *GumgaCrudRepository<>* que recebe por parâmetro a entidade domínio a que esta interface se refere, ou seja o tipo da mesma.
Esta entidade declara uma série de métodos genéricos para a manipulação de registros no banco de dados, como pode ser visto abaixo:

```java
package io.gumga.domain.repository;
//imports
@NoRepositoryBean
public interface GumgaCrudRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, GumgaRepository<T, ID> {

    SearchResult<T> search(QueryObject query);

    Pesquisa<T> search();

    SearchResult<T> search(String hql, Map<String, Object> params);

    SearchResult<T> search(String hql, Map<String, Object> params, int max, int first);

    List<GumgaObjectAndRevision> listOldVersions(ID id);

    <A> SearchResult<A> advancedSearch(String selectQueryWithoutWhere, String countObjt, String ordenationId, QueryObject whereQuery);

    Object genericFindOne(Class clazz, Object id);

    SearchResult<T> findAllWithTenancy();

    T fetchOne(GQuery gQuery);

    List<T> findAll(GQuery gQuery);

    void deletePermanentGumgaLDModel(T entity);

    void deletePermanentGumgaLDModel(ID id);
}
```
Por sua vez a *GumgaCrudRepository* extende de outras entidades como por ex. a *JpaRepository*, interface repositório do Spring Framework.

#### Buscando Registros

Como exemplo vamos implementar e testar algumas buscas de registros no banco de dados a partir de requisições HTTP.<br>
Como já fizemos em outros exemplos, vamos fazer uma requisição GET na API de Pessoa, a rota é a seguinte:
```
http://*servidor*/repositorioBusca-api/api/pessoa
```
Ao fazer essa requisição, um método padrão do Framework irá invocar a busca do Spring, que retorna a lista de todos os registro de Pessoa existentes no banco de dados.

Até aqui nada de novo, serviços CRUD mais básicos estão disponíveis no Framework por padrão, vamos ver agora como podemos criar uma busca personalizada.
> Para mais informações sobre as rotas padrão dos serviços CRUD, consulte a documentação no tópico *Serviços CRUD*

Vamos criar na API de pessoa (classe *PessoaAPI*) um método anotado com o endereço da rota que desejamos, neste caso será uma busca que recebe um valor máximo de *imc*, e retorna uma lista de pessoas com índice de massa corporal abaixo do valor informado
```java
@RequestMapping("/imc/{param}")
public List<Pessoa> getImc(@PathVariable("param") Double param) {
    return pessoaService.getPessoasIMCMenorQue(param);
}
```

Seguimos o padrão que a arquitetura prevê, logo este método deverá invocar um serviço, modelado na classe *PessoaService*, o chamamos de *getPessoasIMCMenorQue(...)*  e podemos ver sua implementação abaixo:
```java
public List<Pessoa> getPessoasIMCMenorQue(Double param){
    return repositoryPessoa.findByImcLessThan(param);
}
```
> Observe que a entidade repository deve ser injetada no Service da entidade domínio, assim como a Service deve ser injetada na API. Esta injeção de dependencia que garante o acesso aos métodos de cada entidade.

Este método de Serviço que por sua vez, chama o método de busca no repositório, o declaramos diretamente na interface Repository *(PessoaRepositorySpring)*
```java
List<Pessoa> findByImcLessThan(Double imc);
```
Como pode ser visto, seguimos o padrão de declaração de método do *Spring Data JPA* que implementa o acesso ao banco de dados a partir de reflexão do nome declarado do método. Para saber mais sobre as possibilidades destas declarações, consulte a documentação oficial do Spring Data JPA.

Considerando a correta implementação destes métodos, podemos fazer uma requisição passando qual o imc máximo desejado na seguinte rota:
```
http://*servidor*/repositorioBusca-api/api/pessoa/imc/20
```
Solicitamos as pessoas com imc < 20, o servidor nos respondeu com os seguintes registros:
```json
[
    {
        "id": 5,
        "oi": null,
        "nome": "Gabriela",
        "idade": 18,
        "peso": 46,
        "altura": 1.65,
        "imc": 16.896235078053262
    },
    {
        "id": 9,
        "oi": null,
        "nome": "Ligia",
        "idade": 47,
        "peso": 68,
        "altura": 1.92,
        "imc": 18.446180555555557
    }
]
```
Experimente buscar outros valores!

No projeto exemplo criamos outra busca que retorna os indivíduos com imc abaixo do limite e que tem a idade máxima passada como parâmetro, o método declarado no Repository é o seguinte:
```java
List<Pessoa> findByImcLessThanEqualAndIdadeLessThanEqual(Double imc, int idade);
```
> É possível criar suas próprias queries personalizadas utilizando a anotação @Query do Spring, consulte a documentação do Spring Data JPA para mais informações.

A rota para acesso a esta busca é a seguinte:
```
http://*servidor*/repositorioBusca-api/api/pessoa/fat?imc=90&idade=18
```
>É necessário passar os parâmetros na URI como no exemplo acima

Desta busca, temos o seguinte resultado:
```json
[
    {
        "id": 3,
        "oi": null,
        "nome": "Mateus",
        "idade": 17,
        "peso": 90,
        "altura": 1.55,
        "imc": 37.460978147762745
    },
    {
        "id": 5,
        "oi": null,
        "nome": "Gabriela",
        "idade": 18,
        "peso": 46,
        "altura": 1.65,
        "imc": 16.896235078053262
    },
    {
        "id": 14,
        "oi": null,
        "nome": "Fernanda",
        "idade": 15,
        "peso": 57,
        "altura": 1.62,
        "imc": 21.719250114311837
    }
]
```
### GumgaQueryDSLRepository e QueryDSL
Outro paradigma de busca utilizado pelo Gumga Framework é o *QueryDSL*.
Por sua vez este possui sua própria super classe Repository que assim como no Spring Data JPA, implementa os métodos básicos CRUD.

Para utilizarmos este padrão devemos nos atentar a alguns detalhes:
* A entidade que define o Tipo deverá ser herdeira de **GumgaModel**.
* É necessário ativar o plugin para geração das entidades Q* que são requeridas para a criação das queries
  * Para ativar este recurso, basta adicionar à lista de dependencias do maven (\*domain/pom.xml), o seguinte plugin:
  ```xml
              <plugin>
                  <groupId>com.mysema.maven</groupId>
                  <artifactId>maven-apt-plugin</artifactId>
                  <version>1.0</version>
                  <executions>
                      <execution>
                          <phase>generate-sources</phase>
                          <goals>
                              <goal>process</goal>
                          </goals>
                          <configuration>
                              <outputDirectory>target/generated-sources/java</outputDirectory>
                              <processor>com.mysema.query.apt.jpa.JPAAnnotationProcessor</processor>
                          </configuration>
                      </execution>
                  </executions>
              </plugin>
  ```
* Por fim, devemos estender a entidade Repositório da classe **GumgaQueryDSLRepository<...>** passando também o tipo a que este repository se refere.
  * Vejamos abaixo a declaração do repositório de Produto
  ```java
  @Repository
public interface ProdutoRepositoryQueryDSL extends GumgaQueryDSLRepository<Produto, Long> {
}
```

Definidos todos estes tópicos, podemos criar nossas. Diferentemente do Spring Data JPA, o QueryDSL demanda a implementação dos métodos com as queries desejadas, por isso vamos construí-las no Service:
```java
public List<Produto> getListGTProdutoPrecoVenda(Double param) {
    return repositoryQueryDSL.findAll(QProduto.produto.precoVenda.gt(param));
}

public List<Produto> getListLOEProdutoMargem(Double param) {
    return repositoryQueryDSL.findAll(QProduto.produto.margemLucro.loe(param));
}

public List<Produto> getListProdutoNomeContains(String param) {
    return repositoryQueryDSL.findAll(QProduto.produto.nome.contains(param));
}
```
> Consulte a documentação oficial do QueryDSL para obter mais informações e instruções de uso dos métodos, sintaxe, etc.

As rotas para acesso a esses métodos que criamos são essas:

```
Produtos com preço de venda maior que X
http://*servidor*/repositorioBusca-api/api/produto/precoVendaGT/X

Produtos com margem menor que X
http://*servidor*/repositorioBusca-api/api/produto/margemLOE/X

Produtos cujo nome contenha o(s) caracter(es) xxx
http://*servidor*/repositorioBusca-api/api/produto/nomeContem/xxx
```

---
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
