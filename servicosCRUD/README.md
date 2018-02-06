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
    >Esta classe estende de AbstractGumgaService

* #### GumgaNoDeleteService
    ##### Class GumgaNoDeleteService<T extends GumgaIdable<ID>,ID extends java.io.Serializable>
    Classe abstrata que contém métodos para criação de serviços para manipulação de entidade (criação, alteração e busca, porém sem exclusão)
    >Esta classe estende de AbstractGumgaService

* #### GumgaReadOnlyService
     ##### Class GumgaReadOnlyService<T extends GumgaIdable<?>,ID extends java.io.Serializable>

    Classe abstrata que contém métodos de serviços somente de leitura da entidade
   
    >Esta classe estende de AbstractGumgaService

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
    
* #### AbstractReadOnlyGumgaAPI

    ##### Class AbstractReadOnlyGumgaAPI<T,ID extends java.io.Serializable>

    API para acesso aos métodos de serviços de somente leitura que o Framework dispõe via requisições REST
    
* #### AbstractNoDeleteGumgaAPI

    ##### Class AbstractNoDeleteGumgaAPI<T,ID extends java.io.Serializable>

    API para acesso aos métodos de serviços que o Framework dispõe via requisições REST sem contemplar operações de         exclusão
        

# Criando Objetos (Create)
Para isso utilizaremos a rota já implementada do framework para criação de objetos padrão
>Lembrando que, esta interface de acesso (API) é automaticamente implementada pelo gerador se a opção foi selecionada no momento da criação da entidade


No projeto exemplo, a rota para Pessoa é a seguinte:
```
http://localhost:8080/servicosCRUD-api/api/pessoa
```
>Subimos o servidor local na porta 8080, caso tenha um servidor diferente, altere a linha de acordo com a necessidade
>Para a manipulação de requisições, usaremos o [Postman](https://www.getpostman.com/)

Enviaremos uma requisição utilizando o método POST contendo o JSON para Pessoa
```json
{
    "nome": "William",
    "idade": 24,
    "peso": 78
}
```
A resposta do servidor deverá conter a mensagem "Pessoa saved successfully" em um JSON contendo a pessoa cadastrada
```json
{
    "code": null,
    "message": "Pessoa saved successfully",
    "details": null,
    "data": {
        "id": "35ED5FD5B91F605F0A3602429876F82E",
        "oi": {
            "value": null
        },
        "nome": "William",
        "idade": 24,
        "peso": 78
    }
}
```
Você pode cadastrar quantas pessoas quiser repetindo estes passos.
Agora podemos verificar os registros salvos!

# Lendo Objetos (Read)
Para fazermos a busca dos registros salvos no servidor, vamos fazer uma requisição com a operação GET
a rota é a mesma de criação:
```
http://localhost:8080/servicosCRUD-api/api/pessoa
```
Uma simples requisição em parâmetro algum deverá retornar todos registros do servidor paginados no seguinte formato:
```json
{
    "pageSize": 10,
    "count": 4,
    "start": 0,
    "values": [
        {
            "id": "35ED5FD5B91F605F0A3602429876F82E",
            "oi": null,
            "nome": "William",
            "idade": 24,
            "peso": 78
        },
        {
            "id": "35ED6715D9BB70D8E97002429876F82E",
            "oi": null,
            "nome": "Mateus",
            "idade": 18,
            "peso": 98.56
        },
        {
            "id": "35ED6721C04D50A6F9F002429876F82E",
            "oi": null,
            "nome": "Caio",
            "idade": 21,
            "peso": 99.6
        },
        {
            "id": "35ED672C0BCCD085FEE602429876F82E",
            "oi": null,
            "nome": "Felipe",
            "idade": 30,
            "peso": 118.98
        }
    ]
}
```
>Operações de busca mais elaboradas serão abordadas em exemplos específicos

# Atualização (Update)

Para atualizar um registro no servidor, devemos utilizar o método PUT, e devemos também passar o id do registro na URI do servidor
O endereço da rota segue o seguinte padrão
```
http://servidor/nomeDaAplicacao-api/api/entidade/{id}
```
No nosso exemplo, iremos atualizar a pessoa William
```
http://localhost:8080/servicosCRUD-api/api/pessoa/35ED5FD5B91F605F0A3602429876F82E
```
Passaremos no corpo da requisição o novo objeto JSON que irá substituir o registro correspondente
```json
	{
	"id" : "35ED5FD5B91F605F0A3602429876F82E",
    "nome": "Willinha",
    "idade": "25",
    "peso": "74.0"
	}
```
>Observe que, passamos no objeto o campo id do registro, isto é necessário pois o framework utiliza o JPA para fazer a manipulação do banco de dados

A resposta do servidor deverá conter a mesma mensagem do método Create, porém agora com o objeto atualizado
```json
{
    "code": null,
    "message": "Pessoa saved successfully",
    "details": null,
    "data": {
        "id": "35ED5FD5B91F605F0A3602429876F82E",
        "oi": {
            "value": null
        },
        "nome": "Willinha",
        "idade": 25,
        "peso": 74
    }
}
```

Faça uma requisição de leitura e verifique seu registro atualizado!

# Delete

O método Delete irá excluir um registro do servidor de acordo com o id passado na URI da requisição
>O padrão da URI segue o mesmo do método Updade

Vamos excluir o registro que criamos e atualizamos para "Willinha", para isso basta criar uma requisição Delete no seguinte endereço:
```
http://localhost:8080/servicosCRUD-api/api/pessoa/35ED5FD5B91F605F0A3602429876F82E
```
>Possivelmente o id do seu registro será diferente, faça a adequação da linha conforme a necessidade

A resposta do servidor deverá conter a mensagem "Pessoa deleted successfully" contida em um JSON no seguinte formato:
```json
{
    "code": null,
    "message": "Pessoa deleted successfully",
    "details": null,
    "data": null
}
```
Faça uma requisição de leitura e verifique que seu registro não retorna mais

---
# Conclusão
Neste exemplo pudemos ver na prática os métodos CRUD, o projeto que criamos possui também uma entidade Time, aproveite e crie e manipule alguns objetos!

Para deixar mais claro o funcionamento da nossa API, criamos uma rota com um método simples de conversão de unidades
>A implementação do método fica na classe API correspondente a entidade desejada, nesse caso criamos na classe "br.com.servicosCRUD.api.TimeAPI"

A rota de acesso é:
```
http://localhost:8080/servicosCRUD-api/api/time/yardToMeter/100
```
> O último número é a variável a ser calculada

Basta passar como parâmetro um número de jardas, no nosso exemplo a resposta foi esta:
```json
"100 jarda(s) equivalem a 91.44 metros!"
```

----
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
