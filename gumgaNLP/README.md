# GumgaNLP
### Processamento de Linguagem Natural

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Uma das funcionalidades oferecidas pelo Gumga Framework é o processamento de linguagem natural chamado de **GumgaNLP**. Esta ferramenta permite a criação de objetos do domínio utilizando comandos de voz transcritos em linguagem natural.<br>
Por padrão esta ferramenta está disponível em Português do Brasil e utiliza uma biblioteca como motor de interpretação e analise textual.

Sua aplicabilidade é muito útil quando combinada com uma ferramenta de transcrição de áudio, oferecendo acessibilidade na execução de operações rotineiras do seu sistema com comandos de voz.

#### Exemplo Prático

Sendo o GumgaNLP uma ferramenta nativa do Framework, seu uso é bastante simplificado, precisamos mapear as entidades que poderão ser gerenciadas por comandos de linguagem natural.<br>
Para nosso exemplo criamos uma entidade Produto com os campos nome, custo, venda, peso e categoria. Segue o exemplo da entidade:
```java
//imports
//...
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
//Getters and Setters
//...
```
A anotação **@GumgaNLPThing** é responsável por mapear o domínio para o gerenciamento da entidade. Observe que precisamos anotar tanto a classe quanto os atributos, sendo que nos campos precisamos passar a expressão chave explícita, isso para que o motor de interpretação textual possa fazer a referência corretamente. Por ex:
```java
@GumgaNLPThing("preço")
private Integer valorVenal;

@GumgaNLPThing("endereço")
private String endereco;

private Long id;
```  

>Observe que não é obrigatório mapear todos os campos da entidade

Feito isso, nosso domínio está pronto para ser invocado pelo *NLP*, criamos uma rota na API para receber um texto de linguagem natural:

```java
@RequestMapping(value = "/texto", method = RequestMethod.POST)
public ResponseEntity nlpText(@RequestHeader String texto, @RequestHeader String verbos) {
    Produto produto = service.nlpTextConverter(texto, verbos);
    return produto != null ? ResponseEntity.status(201).body(produto)
            : ResponseEntity.status(204).body("Incapaz de instanciar um objeto");
}
```
Esta API invoca o método **service.nlpTextConverter(texto, verbos)** que está declarado na classe Service da nossa entidade Produto:

```java
//imports
@Autowired
private GumgaNLP gnlp;
//...
public Produto nlpTextConverter(String texto, String verbos) {
    try {

        List<Object> p = gnlp.createObjectsFromDocument(texto, verbos);
        System.out.println(p);

        Produto produto = (Produto) p.get(0);
        save(produto);
        return produto;
    } catch (Exception e) {
        return null;
    }
}
```
Este é o método que criamos para invocar o processamento do texto, veja que precisamos fazer a injeção de um objeto *GumgaNLP* para termos acesso ao método *createObjectsFromDocument(...)*.


Este método recebe como parâmetro um texto para análise e uma string contendo os verbos da ação a ser realizada separados por virgula (por ex. "criar, lançar, fazer")
> O retorno desta função é uma lista de objetos genéricos, o que permite inclusive que sejam instanciados a partir de uma mesma sentença objetos de diferentes tipos.
<br>Por ex: *Crie uma casa de cor amarela e uma conta para o cliente Caito Silva*
<br>O retorno disso seria uma lista com uma instância de Casa{cor: amarela} e outra de Conta{Cliente: Caito Silva}



 vejamos como manipular essas variáveis:

A rota que criamos recebe uma chave *texto* e outra *verbos* no cabeçalho da requisição. Logo, faremos uma requisição contendo esses parâmetros:

Método: POST<br>
Rota:
```
http://*servidor*/gumgaNLP-api/api/produto/texto
```
Chaves Header:

| texto  | crie um produto com o nome Nabo na categoria Vegetais com custo 2, venda 35 e peso 456 g |
|--------|------------------------------------------------------------------------------------------|
| verbos | criar                                                                                    |
Para fins de melhor entendimento

Ao fazer essa requisição obtemos o seguinte resultado:
```JSON
{
    "id": "360A7C8D9F5F70C69172024287DB433A",
    "oi": {
        "value": null
    },
    "nome": "nabo",
    "custo": 2,
    "venda": 35,
    "peso": 456,
    "categoria": "vegetais"
}
```

Podemos repetir esse procedimento de diversas maneiras, sempre que o gerenciador conseguir entender a frase teremos como resposta uma instância de produto

| texto  | gostaria de criar um produto na categoria Vestuário com o nome Camisa, custo 45 e venda 90 e atribua o peso para 400 gramas |
|--------|-----------------------------------------------------------------------------------------------------------------------------|
| verbos | criar, atribuir                                                                                                             |

Considerando que salvamos a instância de Produto criada no método da classe de serviço, podemos verificar como ficaram persistidas nossas instâncias geradas a partir das frases de linguagem natural:

SELECT * FROM PRODUTO;

| ID                               | OI   | CATEGORIA   | CUSTO   | NOME   | PESO   | VENDA   |
|----------------------------------|------|-------------|---------|--------|--------|---------|
| 360A8728C5F4A0539F66024287DB433A | null | vegetais    | 2       | nabo   | 456    | 35      |
| 360A873DB76FB09E6963024287DB433A | null | vestuário   | 45      | camisa | 400    | 90      |


Em cunho de curiosidade, vejamos o Stack Trace impresso no console quando invocamos o método *createObjectsFromDocument(texto, verbos)*<br>
Este se refere a última chamada que fizemos:

```
Sentence: gostaria de criar um produto  na categoria Vestuário com o nome Camisa, custo 45 e venda 90 e atribua o peso para 400 gramas
  Tokens:
    gostaria   [gostar]     v-fin  COND=1S   
    de         [de]         prp    -         
    criar      [criar]      v-inf  -         
    um         [um]         art    M=S       
    produto    [produto]    n      M=S       
    em         [em]         prp    -         
    a          [o]          art    F=S       
    categoria  [categoria]  n      F=S       
    Vestuário  []           prop   M=S       
    com        [com]        prp    -         
    o          [o]          art    M=S       
    nome       [nome]       n      M=S       
    Camisa     []           prop   F=S       
    ,          []           ,      -         
    custo      [custo]      n      M=S       
    45         []           num    M=P       
    e          [e]          conj-c -         
    venda      [venda]      n      F=S       
    90         []           num    M=P       
    e          [e]          conj-c -         
    atribua    [atribuir]   v-fin  PR=3S=SUBJ
    o          [o]          art    M=S       
    peso       [peso]       n      M=S       
    para       [para]       prp    -         
    400        []           num    F=P       
    gramas     [grama]      n      F=P       
  Chunks: [VP: gostaria ] [PP: de ] [VP: criar ] [NP: um produto ] [PP: em ] [NP: a categoria ] [NP: Vestuário ] [PP: com ] [NP: o nome ] [NP: Camisa ] [NP: custo 45 ] [NP: venda 90 ] [VP: atribua ] [NP: o peso ] [PP: para ] [NP: 400 gramas ]
  Shallow Structure: [P: gostaria ] [PIV: de ] [P: criar ] [ACC: um produto em a categoria Vestuário ] [PIV: com o nome Camisa , custo 45 e venda 90 ] [P: atribua ] [ACC: o peso ] [ADVL: para 400 gramas ]

[Produto { Nome: camisa, Valor de Custo: 45, Valor de Venda: 90, Categoria: vestuário, Peso: 400}]
```

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
