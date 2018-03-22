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
Isso altera o valor do *GumgaThreadScope* que faz com que todos os registros salvos sejam atribuídos com o *organizationCode* da sessão atual.

**É válido lembrar que esses campos só estarão disponíveis se a entidade referida estender de algum *Model* compartilhado, por exemplo o *GumgaSharedModel***
>Por padrão, este valor é atribuído pela requisição e contem o *oi* do usuário.


> Para saber mais sobre o GumgaThreadScope consulte a documentação dedicada a este tópico

O segundo ponto a ser observado é que para cada um dos registros, adicionamos duas organizações ("2." e "3.")

```java
item1.addOrganization("2.");
item1.addOrganization("3.");
```
Esse método é responsável por atribuir os *Organization Id* que irão compartilhar deste registro.<br>
Em outras palavras, sempre que quisermos compartilhar um registro com outra organização, basta adicionar o *oi* da mesma ao registro utilizando este método.

Considerando isso, vamos declarar que o oi "2." se refere ao bar, e "3." é o restaurante.

Vejamos o que isso produz no banco de dados:

| ID   | OI   | GUMGA_ORGS   | GUMGA_USERS   | CATEGORIA   | DESC                                      | NOME                            | PRECO   |
|------|------|--------------|---------------|-------------|-------------------------------------------|---------------------------------|---------|
| 1    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Refrigerante Lata                         | Coca-Cola                       | 4.00    |
| 2    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Garrafa 600ml                             | Água sem Gás                    | 4.00    |
| 3    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Batata Frita Com Queijo e Bacon | Batata Frita com Queijo e Bacon | 18.00   |
| 4    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Polenta Frita                   | Polenta Frita                   | 15.00   |

Vemos que os registros pertencem a organização "1.", porém estão compartilhados com as organizações "2." e "3."<br>
Faremos algumas requisições para entender o que isso significa.

#### Criando Novos Ítens:

Vamos por requisição, adicionar ao cardápio do bar (oi = "2.") algumas bebidas:
Método: POST<br>
Rota:
```
http://*servidor*/registrosCompartilhados-api/api/item
```
RequestHeader:

| Key | Value |
|-----|-------|
| oi  | 2.    |

RequestBody:
```json
{
   "nome":"Heineken",
   "desc":"Cerveja Garrafa 600ml",
   "preco":8,
   "categoria":"BEBIDA"
}
```

Repetiremos esse processo, sabendo que o ítem será persistido na organização (loja) referente ao *oi* passado no cabeçalho.<br>

Sendo assim criaremos itens para o restaurante também, para isso o *oi* deve ser "3."

#### Verificando um Cardápio

Criamos alguns produtos que cada estabelecimento deve oferecer, vamos verificar agora o que temos disponível em cada cardápio, começando pelo bar (oi = "2."):

Método: GET<br>
Rota:
```
http://*servidor*/registrosCompartilhados-api/api/item
```
RequestHeader:

| Key | Value |
|-----|-------|
| oi  | 2.    |

Como resposta, obtemos o seguinte resultado:

```json
{
    "pageSize": 10,
    "count": 6,
    "start": 0,
    "values": [
        {
            "id": 1,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Coca-Cola",
            "desc": "Refrigerante Lata",
            "preco": 4,
            "categoria": "BEBIDA"
        },
        {
            "id": 2,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Água sem Gás",
            "desc": "Garrafa 600ml",
            "preco": 4,
            "categoria": "BEBIDA"
        },
        {
            "id": 3,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Batata Frita com Queijo e Bacon",
            "desc": "Porção de Batata Frita Com Queijo e Bacon",
            "preco": 18,
            "categoria": "COMIDA"
        },
        {
            "id": 4,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Polenta Frita",
            "desc": "Porção de Polenta Frita",
            "preco": 15,
            "categoria": "COMIDA"
        },
        {
            "id": 7,
            "oi": {
                "value": "2."
            },
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Budweiser",
            "desc": "Cerveja garrafa 600ml",
            "preco": 8,
            "categoria": "BEBIDA"
        },
        {
            "id": 8,
            "oi": {
                "value": "2."
            },
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Heineken",
            "desc": "Cerveja garrafa 600ml",
            "preco": 10,
            "categoria": "BEBIDA"
        }
    ]
}
```
Observe que além das bebidas que cadastramos a resposta nos trouxe os itens comuns que mencionamos anteriormente, mesmo sendo o *oi* desses diferentes de "2." (foram retornados pois possuem o *oi* do bar cadastrado no campo *gumgaOrganizations*)

Vamos fazer a mesma requisição, porém desta vez passando o *oi* do restaurante (3.)

```json
{
    "pageSize": 10,
    "count": 6,
    "start": 0,
    "values": [
        {
            "id": 1,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Coca-Cola",
            "desc": "Refrigerante Lata",
            "preco": 4,
            "categoria": "BEBIDA"
        },
        {
            "id": 2,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Água sem Gás",
            "desc": "Garrafa 600ml",
            "preco": 4,
            "categoria": "BEBIDA"
        },
        {
            "id": 3,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Batata Frita com Queijo e Bacon",
            "desc": "Porção de Batata Frita Com Queijo e Bacon",
            "preco": 18,
            "categoria": "COMIDA"
        },
        {
            "id": 4,
            "oi": {
                "value": "1."
            },
            "gumgaOrganizations": ",2.,3.,",
            "gumgaUsers": ",",
            "nome": "Polenta Frita",
            "desc": "Porção de Polenta Frita",
            "preco": 15,
            "categoria": "COMIDA"
        },
        {
            "id": 5,
            "oi": {
                "value": "3."
            },
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Panquecas com Molho Branco",
            "desc": "Panqueca com recheio de ricota e molho branco",
            "preco": 30,
            "categoria": "COMIDA"
        },
        {
            "id": 6,
            "oi": {
                "value": "3."
            },
            "gumgaOrganizations": ",",
            "gumgaUsers": ",",
            "nome": "Lasanha",
            "desc": "Lasanha recheada para duas pessoas",
            "preco": 48,
            "categoria": "COMIDA"
        }
    ]
}
```
Desta vez obtivemos as comidas que cadastramos para o restaurante, além dos itens em comum.

Para finalizar, vejamos o que isso produziu na tabela do banco de dados:

SELECT * FROM ITEM;

| ID   | OI   | GUMGA_ORGS   | GUMGA_USERS   | CATEGORIA   | DESC                                          | NOME                            | PRECO   |
|------|------|--------------|---------------|-------------|-----------------------------------------------|---------------------------------|---------|
| 1    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Refrigerante Lata                             | Coca-Cola                       | 4.00    |
| 2    | 1.   | ,2.,3.,      | ,             | BEBIDA      | Garrafa 600ml                                 | Água sem Gás                    | 4.00    |
| 3    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Batata Frita Com Queijo e Bacon     | Batata Frita com Queijo e Bacon | 18.00   |
| 4    | 1.   | ,2.,3.,      | ,             | COMIDA      | Porção de Polenta Frita                       | Polenta Frita                   | 15.00   |
| 5    | 3.   | ,            | ,             | COMIDA      | Panqueca com recheio de ricota e molho branco | Panquecas com Molho Branco      | 30.00   |
| 6    | 3.   | ,            | ,             | COMIDA      | Lasanha recheada para duas pessoas            | Lasanha                         | 48.00   |
| 7    | 2.   | ,            | ,             | BEBIDA      | Cerveja garrafa 600ml                         | Budweiser                       | 8.00    |
| 8    | 2.   | ,            | ,             | BEBIDA      | Cerveja garrafa 600ml                         | Heineken                        | 10.00   |

Veja que, temos uma única tabela contendo os registros de ambas organizações, onde alguns podem ser compartilhados.

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
