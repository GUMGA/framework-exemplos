# Tenancy


[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Um dos problemas solucionados pelo Framework Gumga é a implementação de uma forma de organizar os registros persistidos de acordo com índices de organização, que podem seguir uma ordem hierárquica ou não.<br>
O padrão adotado é o de **Discriminação por Coluna** *(Column Discriminator)*. Na prática, podemos marcar a qual organização¹ cada registro pertence de acordo com o valor na coluna "oi" das tabelas

> \*1 - por "organização" pode-se entender categorias, setores, fator hierárquico, etc. Porém para simplificar usaremos este termo baseado no acrônimo "oi" (organization id)  

Por exemplo, uma loja chamada Caito possui duas filiais, uma em São Paulo e outra em Curitiba e cada uma delas tem sua própria lista de clientes cadastrados. Sempre que um novo usuário for cadastrado, ele recebe um *oi* que começa em "1." que representa a loja pai (Caito) seguido pelo número da suborganização ("2." para São Paulo e "3." Curitiba).<br>
Então se cadastramos o usuário Felipe na loja de Curitiba, o registro na tabela irá conter além de todos os atributos de usuário, um campo *oi* com o valor "1.3."<br> Veja que não há limites para níveis de suborganizações.<br>
Sendo assim, temos o *oi* como um conjunto de termos separados por ponto (XXX.XXX.XX.) onde cada campo representa um nível de hierarquia.
> O *oi* é uma String, e apesar de adotarmos como boa prática separar cada nível numericamente (por ex. 1.4.12.) nada impede que se use termos alfanuméricos (ex. lojaA.filial4.setor9.)

Vamos a um exemplo mais prático.

### Exemplo de Utilização

Na aplicação de exemplo criamos uma modelagem simples de Produto com alguns campos básicos. Por padrão Produto extende de GumgaModel e por isso possui os campos de *id* e *oi* (caso tenha alguma dúvida sobre o funcionamento do GumgaModel, consulte a documentação do Gumga Framework).

> Os campos *id* e *oi* são gerados automaticamente, e por isso as entidades domínio não implementam os métodos *setId()* e *setOi()*, o que impede a manipulação desses valores de forma direta.

 Criamos também um *RequestInterceptor* para que possamos passar o valor de *oi* no cabeçalho da requisição (veremos mais a frente que o Framework é modelado de forma que seja totalmente compatível com o *Gumga Security*, porém isso não impede que façamos a manipulação de *oi* de forma manual"). Segue abaixo a modelagem da classe Produto:

 ```java
 package br.com.tenancy.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "Produto")
@Table(name = "Produto", indexes = {
        @Index(name = "Produto_gum_oi", columnList = "oi")
})
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_ProdutoId")
public class Produto extends GumgaModel<Long> {

    @Column(name = "nome")
    private String nome;
    @Column(name = "precoCusto")
    private Double precoCusto;
    @Column(name = "precoVenda")
    private Double precoVenda;
    @Column(name = "margemLucro")
    private Integer margemLucro;
    @Column(name = "peso")
    private Double peso;

    public Produto() {
    }
    public Produto(String nome, Double precoCusto, Integer margemLucro, Double peso) {
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
        this.peso = peso;
    }
//Getters and Setters
}

 ```
 > Sobrescrevemos o método *save()* no Service para que seja gerado o valor "precoVenda" a partir do "precoCusto" + "margemLucro" (%). Você pode ver isso na classe *ProdutoService*<br>
 \*/tenancy/tenancy-application/src/main/java/br/com/tenancy/application/ProdutoService.java

 Vamos supor que temos uma rede de lojas de departamentos com várias unidades, e em cada uma dessas unidades temos os mesmos setores (informática, eletrodomésticos, móveis entre outros).<br>
 Com este cenário em mente, vamos cadastrar alguns produtos na loja A:

 Vamos enviar um JSON com os campos nome, precoCusto, margemLucro, peso
 ```json
 {
     "nome": "monitor",
     "precoCusto": 452.8,
     "margemLucro": 25,
     "peso": 5.5
 }
 ```
O método HTTP será POST, e a rota é a seguinte:
 ```
 http://*servidor*/tenancy-api/api/produto
```
Agora um detalhe muito importante, neste exemplo iremos definir o *oi* manualmente, então o primeiro termo representa a loja / filial, e o segundo representa o departamento.<br>
A tabela abaixo representa os valores que vamos utilizar:

|  Depto / Loja    | Loja A         | Loja B         |
| :-------------   | :------------- | :------------- |
|  Informática     |1.1.            |2.1.            |
|  Eletrodomésticos|1.2.            |2.2.            |
|  Móveis          |1.3.            |2.3.            |

Para que o Framework possa cadastrar a instância com o *oi* que precisamos, vamos enviar no cabeçalho da requisição uma chave *oi* informando a que loja e departamento o item corresponde.

| Key | Value     |
| :------------- | :------------- |
| oi      | 1.1.       |

Feita esta requisição o servidor deverá retornar o seguinte JSON:
```JSON
{
    "code": null,
    "message": "Produto saved successfully",
    "details": null,
    "data": {
        "id": 1,
        "oi": {
            "value": "1.1."
        },
        "nome": "monitor",
        "precoCusto": 452.8,
        "precoVenda": 566.9,
        "margemLucro": 25,
        "peso": 5.5
    }
}
```
Seguindo estes parâmetros, podemos cadastrar quantos produtos quisermos, seguindo a tabela de valores de *oi*
> É valido ressaltar que se estivermos utilizando o módulo Gumga Security, o *oi* é gerado automaticamente de acordo com as organizações cadastradas, fazendo que com todo esse processo de definição de *oi* seja automatizado.

Para facilitar criamos um [Seed](https://github.com/GUMGA/framework-exemplos/tree/master/seeds) com os valores necessários para visualização.<br>
Vejamos como fica a tabela no banco de dados:

| ID ▼ | OI   | MARGEMLUCRO   | NOME   | PESO   | PRECOCUSTO   | PRECOVENDA   |
|------|------|---------------|-------------------------------|--------|--------------|--------------|
| 1 | 1.1. | 25 | Notebook Samsung | 4.6 | 1200.0 | 1500.9 |
| 2 | 1.1. | 12 | Impressora Hp Deskjet | 3.7 | 125.0 | 140.9 |
| 3 | 1.1. | 14 | MacBook Air | 1.6 | 3896.0 | 4442.9 |
| 4 | 1.1. | 17 | IMac Retina 5K | 7.6 | 12850.0 | 15035.9 |
| 5 | 2.1. | 42 | Multifuncional Epson | 8.6 | 752.0 | 1068.9 |
| 6 | 2.1. | 61 | Hd Sata 1tb | 0.8 | 189.0 | 305.9 |
| 7 | 2.1. | 19 | Tablet Samsung | 1.2 | 610.0 | 726.9 |
| 8 | 1.2. | 28 | Refrigerador Brastemp | 80.6 | 980.0 | 1255.9 |
| 9 | 1.2. | 21 | Micro-ondas Brastemp | 21.4 | 458.0 | 555.9 |
| 10 | 1.2. | 49 | Fogão Top Gourmet | 16.4 | 1200.0 | 1788.9 |
| 11 | 2.2. | 34 | Fogão Havana | 45.6 | 250.0 | 335.9 |
| 12 | 2.2. | 27 | Frigobar Consul | 46.4 | 458.0 | 582.9 |
| 13 | 2.2. | 29 | Lavadora de Roupas Electrolux | 90.4 | 893.0 | 1152.9 |
| 14 | 1.3. | 39 | Guarda Roupa | 110.6 | 890.0 | 1238.9 |
| 15 | 1.3. | 56 | Cadeira De Escritório | 16.0 | 298.0 | 465.9 |
| 16 | 1.3. | 29 | Kit 6 Poltronas | 120.4 | 1080.0 | 1394.9 |
| 17 | 2.3. | 39 | Sofá 4 Lugares | 98.6 | 1090.0 | 1516.9 |
| 18 | 2.3. | 19 | Sofá Cama Casal | 163.0 | 1204.0 | 1433.9 |
| 19 | 2.3. | 75 | Painel para TV | 39.4 | 120.0 | 210.9 |
| 20 | 2.3. | 12 | Mesa de Jantar | 46.4 | 750.0 | 841.9 |

Veja que todos os produtos estão cadastrados em uma única tabela, independente a que loja ou departamento o mesmo pertença, a única distinção entre eles é a coluna OI.

### Conclusão

Esse modo de discriminação de dados torna a manipulação dos registros simples e objetiva, podemos a partir deste modelo facilmente aplicar qualquer mecanismo de busca e recuperação de dados de acordo com a necessidade.<br>
Por exemplo, se quisermos obter todos os produtos da loja B:
```sql
SELECT * FROM PRODUTO WHERE OI LIKE '2.%';
```
| ID   | OI   | MARGEMLUCRO   | NOME   | PESO   | PRECOCUSTO   | PRECOVENDA   |
|------|------|---------------|-------------------------------|--------|--------------|--------------|
| 5 | 2.1. | 42 | Multifuncional Epson | 8.6 | 752.0 | 1068.9 |
| 6 | 2.1. | 61 | Hd Sata 1tb | 0.8 | 189.0 | 305.9 |
| 7 | 2.1. | 19 | Tablet Samsung | 1.2 | 610.0 | 726.9 |
| 11 | 2.2. | 34 | Fogão Havana | 45.6 | 250.0 | 335.9 |
| 12 | 2.2. | 27 | Frigobar Consul | 46.4 | 458.0 | 582.9 |
| 13 | 2.2. | 29 | Lavadora de Roupas Electrolux | 90.4 | 893.0 | 1152.9 |
| 17 | 2.3. | 39 | Sofá 4 Lugares | 98.6 | 1090.0 | 1516.9 |
| 18 | 2.3. | 19 | Sofá Cama Casal | 163.0 | 1204.0 | 1433.9 |
| 19 | 2.3. | 75 | Painel para TV | 39.4 | 120.0 | 210.9 |
| 20 | 2.3. | 12 | Mesa de Jantar | 46.4 | 750.0 | 841.9 |

Outro exemplo, queremos todos os produtos do departamento Informática de todas as lojas:
```sql
SELECT * FROM PRODUTO WHERE OI LIKE '%1.';
```
| ID   | OI   | MARGEMLUCRO   | NOME   | PESO   | PRECOCUSTO   | PRECOVENDA   |
|------|------|---------------|-----------------------|--------|--------------|--------------|
| 1 | 1.1. | 25 | Notebook Samsung | 4.6 | 1200.0 | 1500.9 |
| 2 | 1.1. | 12 | Impressora Hp Deskjet | 3.7 | 125.0 | 140.9 |
| 3 | 1.1. | 14 | MacBook Air | 1.6 | 3896.0 | 4442.9 |
| 4 | 1.1. | 17 | IMac Retina 5K | 7.6 | 12850.0 | 15035.9 |
| 5 | 2.1. | 42 | Multifuncional Epson | 8.6 | 752.0 | 1068.9 |
| 6 | 2.1. | 61 | Hd Sata 1tb | 0.8 | 189.0 | 305.9 |
| 7 | 2.1. | 19 | Tablet Samsung | 1.2 | 610.0 | 726.9 |

Neste exemplo criamos os valores de OI manualmente, porém na prática devemos modelar uma forma automatizada para a geração destes valores.<br>
Como citado anteriormente, se a sua aplicação estiver utilizando o Gumga Security, este sistema de discriminação estará diretamente vinculado ao software / instância cadastrado, e um *RequestInterceptor* irá coletar as informações das requisições e irá alimentar o *GumgaThreadScope*, que contém as informações de usuário necessárias para a geração do *oi*.

---
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
