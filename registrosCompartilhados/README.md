# Registros Compartilhados (Gumga Shared)

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Quando trabalhamos com MultiTenancy, ou bancos de dados unificados com registros que utilizam discriminação por coluna, é necessário que tenhamos um modo de compartilhar registros entre diferentes organizações ou usuários.
> Entenda mais sobre MultiTenancy na documentação "Tenancy"

O Framework Gumga dispõe de uma série de modelos de entidade que implementam os campos *gumgaOrganizations* e *gumgaUsers*. Estes campos são strings que podem conter os códigos e usuários (separados por vírgula) que poderão ter acesso ao objeto instanciado.

Isso é muito útil caso seja necessário criar registros que devem ser comuns a vários usuários ou organizações, como grupos de configurações ou arquivos (dados) que devem ser acessíveis a várias pessoas.

> Para entender mais sobre as classes de modelagem veja a documentação específica "GumgaModel"

#### Exemplo

A melhor maneira de entender o funcionamento de registros compartilhados é vendo na prática um uso comum.<br>

Criamos um projeto que gerencia os itens de cardápio de uma rede de estabelecimentos do setor de alimentação. Inicialmente temos duas lojas, um restaurante e um bar, a questão que vale a pena considerar é que existem itens no cardápio que são comuns em ambos estabelecimentos, são esses:

| nome                            | desc                                      | preço |
|---------------------------------|-------------------------------------------|-------|
| Coca-Cola                       | Refrigerante Lata                         | 5     |
| Água sem Gás                    | Garrafa 600ml                             | 4     |
| Batata Frita com Queijo e Bacon | Porção de Batata Frita Com Queijo e Bacon | 18    |
| Polenta Frita                   | Porção de Polenta Frita                   | 15    |

Considerando que independente de qual seja a organização (loja) que buscar os ítens do cardápio, estes registros devem ser retornados.

> Teoricamente poderíamos resolver este problema de várias maneiras, porém é sempre um bom caminho nunca duplicar registros em um banco de dados sem uma necessidade incontornável.

Criamos um Seed para o carregamento desses valores no banco de dados no momento da inicialização, segue a implementação:

> Para saber mais sobre como utilizar seeds no Gumga Framework, consulte a documentação "Seeds"

```java
@Override
public void loadSeed() throws IOException {
    if (itemService.hasData()) {
        return;
    }
    GumgaThreadScope.organizationCode.set("1.");
    Item item1 = new Item("Coca-Cola", "Refrigerante Lata", new BigDecimal(4), Categorias.BEBIDA);
    item1.addOrganization("2.");
    item1.addOrganization("3.");
    itemService.save(item1);
    Item item2 = new Item("Água sem Gás", "Garrafa 600ml", new BigDecimal(4), Categorias.BEBIDA);
    item2.addOrganization("2.");
    item2.addOrganization("3.");
    itemService.save(item2);
    Item item3 = new Item("Batata Frita com Queijo e Bacon", "Porção de Batata Frita Com Queijo e Bacon", new BigDecimal(18), Categorias.COMIDA);
    item3.addOrganization("2.");
    item3.addOrganization("3.");
    itemService.save(item3);
    Item item4 = new Item("Polenta Frita", "Porção de Polenta Frita", new BigDecimal(15), Categorias.COMIDA);
    item4.addOrganization("2.");
    item4.addOrganization("3.");
    itemService.save(item4);
}
```
Existem dois detalhes a serem observados nesse trecho de código, o primeiro é que fizemos o *set* do *organizationCode* no início:
```java
GumgaThreadScope.organizationCode.set("1.");
```
Isso altera o valor do *GumgaThreadScope* que faz com que todos os registros salvos, sejam atribuídos com os parâmetros do usuário que fez a requisição

> Para saber mais sobre o GumgaThreadScope consulte a documentação dedicada a este tópico

O segundo ponto a ser observado é que para cada um dos registros, adicionamos duas organizações ("2." e "3.")

```java
item1.addOrganization("2.");
item1.addOrganization("3.");
```
Esse método é responsável por atribuir os *Organization Id* que irão compartilhar deste registro.<br>
Em outras palavras, sempre que quisermos compartilhar um registro com outra organização, basta adicionar o *oi* da mesma ao registro utilizando este método.

Considerando isso, consideraremos que o oi "2." se refere ao bar, e "3." é o restaurante.

Vejamos o que isso produz no banco de dados:

| ID   | OI   | GUMGA_ORGS   | GUMGA_USERS   | CATEGORIA   | DESC                                      | NOME                            | PRECO   |
|------|------|--------------|---------------|-------------|-------------------------------------------|---------------------------------|---------|
| 1    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Refrigerante Lata                         | Coca-Cola                       | 4.00    |
| 2    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Garrafa 600ml                             | Água sem Gás                    | 4.00    |
| 3    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Batata Frita Com Queijo e Bacon | Batata Frita com Queijo e Bacon | 18.00   |
| 4    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Polenta Frita                   | Polenta Frita                   | 15.00   |

Vemos que os registros pertencem a organização "1.", porém estão compartilhados com as organizações "2." e "3."<br>
Faremos algumas requisições para entender o que isso significa.

### Criando novos itens:

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
