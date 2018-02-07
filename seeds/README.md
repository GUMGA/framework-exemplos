# Criando Seeds
-
[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)
<br>O Gumga Framework disponibiliza um utilitário para a criação de seeds, ou seja objetos das suas entidades para popular o banco de dados com valores iniciais
As aplicações para isso vão desde o carregamento de dados, valores ou configurações padrão até testes de funcionalidades.

O projeto exemplo contém duas entidades com seeds implementados
##### Classe Produto
```java
package br.com.seeds.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "Produto")
@Table(name = "Produto", indexes = {
    @Index(name = "Produto_gum_oi", columnList = "oi")
})
public class Produto extends GumgaModelUUID {
    @Column(name = "nome")
	private String nome;
    @Column(name = "valor")
	private Double valor;
    @Column(name = "peso")
	private Double peso;
    public Produto() {}

	public Produto(String nome, Double valor, Double peso) {
		this.nome = nome;
		this.valor = valor;
		this.peso = peso;
	}
//Getters and Setters
}
```
##### Classe Carrinho
```java
package br.com.seeds.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "Carrinho")
@Table(name = "Carrinho", indexes = {
    @Index(name = "Carrinho_gum_oi", columnList = "oi")
})
public class Carrinho extends GumgaModelUUID {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();
    @Column(name = "valorFrete")
	private Double valorFrete;
    @Column(name = "nomeUsuario")
	private String nomeUsuario;

    public Carrinho() {}

	public Carrinho(Double valorFrete, String nomeUsuario) {
		this.valorFrete = valorFrete;
		this.nomeUsuario = nomeUsuario;
	}
   //Getters and Setters
}
```
Criamos também uma classe auxiliar para relacionar produto com carrinho
##### Classe ItemCarrinho
```java
package br.com.seeds.domain.model;
//imports
@GumgaMultitenancy
@Audited
@Entity(name = "ItemCarrinho")
@Table(name = "ItemCarrinho", indexes = {
    @Index(name = "ItemCarrinho_gum_oi", columnList = "oi")
})
public class ItemCarrinho extends GumgaModelUUID {
    @ManyToOne
	private Produto produto;
    @Column(name = "quantidade")
	private Integer quantidade;

    public ItemCarrinho() {}

	public ItemCarrinho(Produto produto) {
    	this.quantidade = 1;
		this.produto = produto;
	}
//Getters and Setters
}
```
> É válido lembrar que, a implementação de seeds segue as mesmas regras que estiverem definidas para a criação de objetos, ou seja os objetos persistidos no banco de dados na inicialização do sistema, por padrão são entidades válidas em nível persistência e regras de negocio

### Criando Seeds

Para este exemplo, criamos uma classe de seed para cada entidade do nosso domínio, o pacote que aloca essas classes por padrão é essa:
**NomeDoSeuProjeto/NomeDoSeuProjeto-api/src/main/java/br/com/seeds/api/seed/\*.java**
>Para o nosso exemplo, o pacote é **seeds/seeds-api/src/main/java/br/com/seeds/api/seed/**

##### Classe ProdutoSeed
Classe que implementa a criação do seed de produto
```java
package br.com.seeds.api.seed;
//imports

@Component
public class ProdutoSeed implements AppSeed {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    GumgaLoggerService gumgaLoggerService;
    @Override
    public void loadSeed() throws IOException {
        if (produtoService.hasData()) {
            /**Este if verifica se já existem objetos desta entidade persistidos*/
            gumgaLoggerService.logToFile("Dados encontrados no repositório", 1);
            return;
        }
        Produto produto1 = new Produto("Cartão de Memória", 89.90, 0.09);
        produtoService.save(produto1);
        Produto produto2 = new Produto("Placa Mãe", 569.90, 2.12);
        produtoService.save(produto2);
        Produto produto3 = new Produto("Fone Headset", 128.50, 1.79);
        produtoService.save(produto3);
        Produto produto4 = new Produto("Monitor Gamer", 799.90, 5.90);
        produtoService.save(produto4);
        Produto produto5 = new Produto("Processador", 1289.90, 1.25);
        produtoService.save(produto5);
        Produto produto6 = new Produto("Antivírus", 69.90, 0.0);
        produtoService.save(produto6);
        Produto produto7 = new Produto("Mouse sem Fio", 199.90, 0.6);
        produtoService.save(produto7);
    }
}

```
o método loadSeed() serve para a instanciação dos objetos a serem persistidos na inicialização do servidor.
Você pode implementar qualquer tipo de lógica para a obtenção desses dados, inclusive
fazer chamada a uma API externa para geração de informações;

Neste exemplo criamos algumas instâncias de maneira manual, utilizando o construtor que criamos
na classe Produto

> Como dito anteriormente, a criação de seeds segue as mesmas regras de criação de objetos que tenha sido implementado, logo a chamada do método produtoService.save se utiliza do próprio serviço CRUD implementado manipular (salvar) os seeds no banco de dados

##### Classe CarrinhoSeed

```java
package br.com.seeds.api.seed;
//imports

@Component
public class CarrinhoSeed implements AppSeed {

    private static final Random random = new Random();
    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Override
    public void loadSeed() throws IOException {
        if (carrinhoService.hasData()) {
            /**Este if verifica se já existem objetos desta entidade persistidos*/
            gumgaLoggerService.logToFile("Dados encontrados no repositório", 1);
            System.out.println("Dados encontrados no repositório");
            return;
        }
        Carrinho carrinho1 = new Carrinho(9.90, "Caio");
        Carrinho carrinho2 = new Carrinho(10.90, "Mateus");
        Carrinho carrinho3 = new Carrinho(6.90, "Felipe");
        List<Carrinho> carrinhos = new ArrayList<>();
        carrinhos.add(carrinho1);
        carrinhos.add(carrinho2);
        carrinhos.add(carrinho3);
        List<Produto> produtos = produtoService.pesquisa(new QueryObject()).getValues();
        for (Carrinho c : carrinhos) {
            int index = random.nextInt(produtos.size());
            for (int i = 0; i < index + 1; i++) {
                System.out.println(c.getNomeUsuario());
                c.getItens().add(new ItemCarrinho(produtos.get(random.nextInt(produtos.size()))));
            }
            carrinhoService.save(c);
        }
    }
}

```
Neste exemplo criamos algumas instâncias de maneira manual, utilizando o construtor que criamos
na classe Carrinho.
A atribuição de itens ao carrinho está sendo feita de maneira aleatória, tanto a quantidade
de ítens no carrinho quanto quais itens são estes.

Observe que, tanto na classe ProdutoSeed quanto na Classe CarrinhoSeed, fizemos uma chamada do método **hasData()** do service, isto serve para que seja verificado se existem dados persistidos no banco de dados, ou seja, nosso sistema só será populado caso nenhum objeto da entidade referida estiver gravado no banco de dados.

o método é implementado na classe de Serviço da entidade, e segue como no exemplo para a classe Produto:

```java
public boolean hasData() {
    return repositoryProduto.count() > 0;
}
```
### Carregando Seeds

Até agora vimos como implementar as regras de criação de seeds e entendemos como eles são persistidos,
agora só precisamos chamar estes métodos de criação no momento do deploy do sistema.
Para isso o Framework dispõe a classe seed.java que implementa os métodos necessários
>Esta classe fica por padrão no mesmo pacote onde criamos nossos Seeds <br>
**seeds/seeds-api/src/main/java/br/com/seeds/api/seed/** <br> (para o nosso exemplo)

##### Classe seed
```java
package br.com.seeds.api.seed;

//imports
@Component
class Seed implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Autowired
    private RegisterApplication registerApplication;

    private AtomicBoolean started = new AtomicBoolean(false);

    @Autowired
    private ProdutoSeed produtoSeed;

    @Autowired
    private CarrinhoSeed carrinhoSeed;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (started.get()) {
            return;
        }
        started.set(true);
        registerApplication.register();
        gumgaLoggerService.logToFile("Start ", 1);
        for (AppSeed seed : seeds()) {
            try {
                seed.loadSeed();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        gumgaLoggerService.logToFile("End ", 2);
    }
    private List<AppSeed> seeds() {
        List<AppSeed> list = new LinkedList<>();
        list.add(produtoSeed);
        list.add(carrinhoSeed);
        return list;
    }
}

```
Basta adicionar o objeto correspondente as classes de seed criadas na "list" do método seeds() que o Framework se encarrega de fazer a chamada de criação na inicialização do servidor.


----
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
