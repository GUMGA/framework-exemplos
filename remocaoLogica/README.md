# Remoção Lógica


[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

*Disponível a partir da versão 3.2.3*

A fim de evitar a destruição de dados persistidos o Gumga Framework dispõe de um modelo de entidade capaz de gerir a exclusão de registros no banco de dados.<br>
Trata-se da super classe **GumgaLDModel**, uma classe de modelagem com *id* sequencial que possui um campo lógico para a inativação de objetos (*gumgaActive*).

> Para entender mais sobre as classes de modelagem do Gumga Framework, veja a documentação sobre [GumgaModel](https://github.com/GUMGA/framework-exemplos/tree/develop/gumgaModel)

O projeto exemplo dispõe de uma modelagem simples para visualizarmos as operações de exclusão. Para isso criamos um [Seed](https://github.com/GUMGA/framework-exemplos/tree/master/seeds) de Pessoa com os campos *nome, peso e altura.*

Podemos visualizar os dados fazendo uma requisição com método GET na seguinte rota:
```
http://*servidor*/remocaoLogica-api/api/pessoa/
```
Como resposta, o servidor deverá retornar os seguintes valores:
```json
{
    "pageSize": 10,
    "count": 3,
    "start": 0,
    "values": [
        {
            "id": 1,
            "oi": {
                "value": "1."
            },
            "gumgaActive": true,
            "nome": "Caito",
            "peso": 95.2,
            "altura": 1.75
        },
        {
            "id": 2,
            "oi": {
                "value": "1."
            },
            "gumgaActive": true,
            "nome": "Mateus",
            "peso": 98.4,
            "altura": 1.58
        },
        {
            "id": 3,
            "oi": {
                "value": "1."
            },
            "gumgaActive": true,
            "nome": "Felipe",
            "peso": 126.9,
            "altura": 1.72
        }
    ]
}
```

Observe que em todos os registros temos o campo "gumgaActive = true", isso significa que nenhum registro foi deletado.<br>
Vamos agora fazer uma requisição utilizando o método DELETE referenciando o id: 1
```
http://*servidor*/remocaoLogica-api/api/pessoa/1
```
O servidor deverá responder com um campo *"message": "Pessoa deleted successfully"*

A mesma requisição GET feita anteriormente, agora deverá retornar o seguinte conteúdo:
```json
{
    "pageSize": 10,
    "count": 2,
    "start": 0,
    "values": [
        {
            "id": 2,
            "oi": {
                "value": "1."
            },
            "gumgaActive": true,
            "nome": "Mateus",
            "peso": 98.4,
            "altura": 1.58
        },
        {
            "id": 3,
            "oi": {
                "value": "1."
            },
            "gumgaActive": true,
            "nome": "Felipe",
            "peso": 126.9,
            "altura": 1.72
        }
    ]
}
```
O registro "Caito" não é mais retornado na resposta do servidor quando solicitamos todos os dados de Pessoa, porém vamos dar uma olhada no banco de dados:

> O console do banco de dados H2 estará disponível no seguinte endereço:
 ```
 endereço: http://*servidor*/remocaoLogica-api/h2-console
 url: jdbc:h2:mem:studio;MVCC=true
 username: sa
 password: sa
 ```

 SELECT * FROM PESSOA;

 | ID   | OI   | GUMGA_ACTIVE   | ALTURA   | NOME   | PESO   |
 |------|------|----------------|----------|--------|--------|
 | 1 | 1. | FALSE | 1.75 | Caito | 95.2 |
 | 2 | 1. | TRUE | 1.58 | Mateus | 98.4 |
 | 3 | 1. | TRUE | 1.72 | Felipe | 126.9 |

 Veja que, o registro "Caito" continua persistido, porém com o campo *GUMGA_ACTIVE* falso, representando a remoção lógica.

### Exlusão Permanente
