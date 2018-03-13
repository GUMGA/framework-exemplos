# GumgaCustomFields

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

O Framework Gumga oferece uma funcionalidade para adicionar campos a entidades domínio sem a necessidade de alterar a estrutura do banco de dados, isso pode ser útil para oferecer ao usuário campos personalizados em cadastros genéricos, aplicáveis em diferentes domínios.
Iremos demonstrar no exemplo como utilizar um campo customizado. Mas antes vamos entender mais sobre as classes envolvidas.

### GumgaCustomizableModel

O Framework Gumga possui uma classe de modelagem para as entidades domínio que podem receber campos customizados, esta classe estende de GumgaModel, ou seja possui por padrão implementação de *id* sequencial.
>Para entender mais sobre as classes de modelagem consulte a documentação específica sobre GumgaModel

Por sua vez o modelo *GumgaCustomizableModel* fornece as entidades filhas uma coleção (Map) de objetos do tipo **GumgaCustomFieldValue**, estes objetos possuem valores e referências a campos customizados. Esta refeência é modelada por um objeto **GumgaCustomField**, que modela o campo em sí, referenciando a qual entidade este campo se refere, o tipo de dados que será recebido etc.


Observe que, quando adicionamos um campo a uma entidade, na verdade criamos um objeto que possui uma referência à classe na qual desejamos adicionar um campo, e depois os valores cadastrados neste campo são salvos em objeto relacional.<br>
Esta abordagem permite que criemos campos adicionais a entidades sem a necessidade de alterar a estrutura do banco de dados.
Vejamos na prática como isso funciona:

### Exemplo Prático

Na nossa aplicação temos duas entidades distintas, Pessoa e Conta. Os campos são os seguintes:
```java
//Pessoa
@Column(name = "nome")
private String nome;
@Column(name = "documento")
private String documento;
```
```java
//Conta
@Column(name = "agencia")
private String agencia;
@Column(name = "numero")
private Integer numero;
@Column(name = "nome")
private String nome;
```
Nossa intenção é criar campos customizáveis nessas entidades, para pessoa queremos um campo de texto "tipoPessoa" (física ou jurídica) e em conta, adicionaremos um campo numérico "operacao".
>Neste exemplo queremos simplesmente demonstrar como utilizar atributos customizados, a decisão de utilizar este artifício ou criar um campo diretamente na classe domínio fica a critério da modelagem de dados adotada pelo programador.

Podemos criar um novo campo customizado para qualquer entidade que estender de *GumgaCustomizableModel* vejamos abaixo como ficam nossas classes
```java
//Pessoa
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_PessoaId")
public class Pessoa extends GumgaCustomizableModel<Long> {
  /**
  ...
  **/
}
```
```java
//Conta
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_ContaId")
public class Conta extends GumgaCustomizableModel<Long> {
  /**
  ...
  **/
}
```

Como vimos anteriormente, o que implementa um campo customizado é uma classe do Framework, logo temos a entidade **GumgaCustomFieldService** e uma **GumgaCustomFieldAPI** para a manipulação destes objetos.<br>
A API dispõe de uma rota para acesso aos serviços básicos do Framework, o endereço é:
```
*servidor*/nomeDaAplicacao-api/api/gumgacustomfield
```
#### Criando um novo campo customizados

Para criar um novo campo customizado basta que seja enviado à rota de acesso um objeto contendo os campos requeridos, segue o exemplo abaixo:
```json
{  
   "active":"true",
   "clazz":"br.com.customFields.domain.model.Pessoa",
   "colSize":"12",
   "name":"tipoPessoa",
   "description":"Define o tipo do documento",
   "type":"TEXT",
   "validationScript":"true"
}
```
Faremos uma requisição com o método POST na seguinte rota:
```
http://*servidor*/customFields-api/api/gumgacustomfield
```

Criaremos também da mesma maneira um campo para a classe Conta:

```json
Formatted JSON Data
{  
   "active":"true",
   "clazz":"br.com.customFields.domain.model.Conta",
   "colSize":"12",
   "name":"operacao",
   "description":"Operação da conta",
   "type":"NUMBER",
   "validationScript":"true"
}
```
Por padrão são criadas no banco duas tabelas, **GUMGA_CTM_FLD** e **GUMGA_FLD_VLE** uma para os registros e outra para os valores associados. Observe que mesmo os campos se referindo a entidades distintas eles são registrados como tuplas de uma mesma tabela<br>

SELECT * FROM GUMGA_CTM_FLD;

| ID   | OI   | ACTIVE   | CLAZZ                                   | COLSIZE   | DEFAULTVALUESCRIPT   | DESCRIPTION                | FIELDGROUP   | NAME       | OPTIONLABELFIELD   | OPTIONVALUEFIELD   | OPTIONS   | OPTIONSCOLLECTION   | TRANSLATEKEY       | TYPE   | VALIDATIONDESCRIPTION   | VALIDATIONSCRIPT   | VISUALIZATIONORDER   |
|------|------|----------|-----------------------------------------|-----------|----------------------|----------------------------|--------------|------------|--------------------|--------------------|-----------|---------------------|--------------------|--------|-------------------------|--------------------|----------------------|
| 1    | null | TRUE     | br.com.customFields.domain.model.Pessoa | 12        | ''                   | Define o tipo do documento | $DEFAULT     | tipoPessoa | null               | null               | null      | null                | class.custom_field | TEXT   |                         | true               | 10.0                 |
| 2    | null | TRUE     | br.com.customFields.domain.model.Conta  | 12        | ''                   | Operação da conta          | $DEFAULT     | operacao   | null               | null               | null      | null                | class.custom_field | NUMBER |                         | true               | 10.0                 |

Agora ao criar registros podemos utilizar este novo campo, vejamos como cadastrar uma nova pessoa utilizando o campo criado:

> Para saber mais sobre como utilizar as API's para criação de objetos, consulte a documentação sobre operações CRUD

Vamos enviar uma requisição POST na seguinte rota:
```
http://*servidor*/customFields-api/api/pessoa
```
No corpo da requisição enviaremos o objeto Pessoa, contendo "nome", "documento" e o objeto "gumgaCustomFields" conforme segue o exemplo:

```JSON
{  
   "nome":"Caito",
   "documento":"081012",
   "gumgaCustomFields":{  
      "tipoPessoa":{  
         "field":{  
            "id":1,
            "oi":null,
            "clazz":"br.com.customFields.domain.model.Pessoa",
            "name":"tipoPessoa",
            "description":"Define o tipo do documento",
            "active":true,
            "type":"TEXT",
            "validationDescription":"",
            "validationScript":"true",
            "defaultValueScript":"''",
            "options":null,
            "optionValueField":null,
            "optionLabelField":null,
            "optionsCollection":null,
            "visualizationOrder":10,
            "fieldGroup":"$DEFAULT",
            "translateKey":"class.custom_field",
            "colSize":12
         },
         "textValue":"Pessoa Física"
      }
   }
}
```
Observe que precisamos passar dentro de "gumgaCustomFields" um objeto "field", que é o campo ao qual queremos associar o valor.

Da mesma maneira vamos criar um registro de Conta:

Rota:
```
http://*servidor*/customFields-api/api/conta
```
```JSON
{  
   "agencia":"0233",
   "numero": 67180,
   "nome":"Caito da Silva",
   "gumgaCustomFields":{  
      "tipoPessoa":{  
         "field":{
            "id": 2,
            "oi": null,
            "clazz": "br.com.customFields.domain.model.Conta",
            "name": "operacao",
            "description": "Operação da conta",
            "active": true,
            "type": "NUMBER",
            "validationDescription": "",
            "validationScript": "true",
            "defaultValueScript": "''",
            "options": null,
            "optionValueField": null,
            "optionLabelField": null,
            "optionsCollection": null,
            "visualizationOrder": 10,
            "fieldGroup": "$DEFAULT",
            "translateKey": "class.custom_field",
            "colSize": 12
        },
         "numberValue": 13
      }
   }
}
```
Podemos agora buscar os registros e observar como o servidor responde:<br>
Método: GET<br>
Rota:
```
http://*servidor*/customFields-api/api/pessoa/1
```
Resultado:
```JSON
{
    "id": 1,
    "oi": null,
    "gumgaCustomFields": {
        "tipoPessoa": {
            "id": 1,
            "oi": null,
            "field": {
                "id": 1,
                "oi": null,
                "clazz": "br.com.customFields.domain.model.Pessoa",
                "name": "tipoPessoa",
                "description": "Define o tipo do documento",
                "active": true,
                "type": "TEXT",
                "validationDescription": "",
                "validationScript": "true",
                "defaultValueScript": "''",
                "options": null,
                "optionValueField": null,
                "optionLabelField": null,
                "optionsCollection": null,
                "visualizationOrder": 10,
                "fieldGroup": "$DEFAULT",
                "translateKey": "class.custom_field",
                "colSize": 12
            },
            "gumgaModelId": 1,
            "textValue": "Pessoa Física",
            "numberValue": null,
            "dateValue": null,
            "logicValue": null
        }
    },
    "nome": "Caito",
    "documento": "081012"
}
```

Da mesma maneira, vamos buscar o registro de Conta que criamos<br>
Método: GET<br>
Rota:
```
http://*servidor*/customFields-api/api/conta/1
```
Resultado:
```JSON
{
    "id": 1,
    "oi": null,
    "gumgaCustomFields": {
        "operacao": {
            "id": 2,
            "oi": null,
            "field": {
                "id": 2,
                "oi": null,
                "clazz": "br.com.customFields.domain.model.Conta",
                "name": "operacao",
                "description": "Operação da conta",
                "active": true,
                "type": "NUMBER",
                "validationDescription": "",
                "validationScript": "true",
                "defaultValueScript": "''",
                "options": null,
                "optionValueField": null,
                "optionLabelField": null,
                "optionsCollection": null,
                "visualizationOrder": 10,
                "fieldGroup": "$DEFAULT",
                "translateKey": "class.custom_field",
                "colSize": 12
            },
            "gumgaModelId": 1,
            "textValue": null,
            "numberValue": 13,
            "dateValue": null,
            "logicValue": null
        }
    },
    "agencia": "0233",
    "numero": 67180,
    "nome": "Caito da Silva"
}
```
#### Persistência de campos customizados

Como dito anteriormente, este artifício não altera a estrutura do banco de dados, considerando as modificações feitas até agora no exemplo vamos ver como estão relacionados os dados no banco:<br>

SELECT * FROM PESSOA;

| ID   | OI   | DOCUMENTO   | NOME   |
|------|------|-------------|--------|
| 1    | null | 081012      | Caito  |

SELECT * FROM CONTA;

| ID   | OI   | AGENCIA   | NOME           | NUMERO   |
|------|------|-----------|----------------|----------|
| 1    | null | 0233      | Caito da Silva | 67180    |

Observe que Pessoa e Conta não possuem nenhum campo "extra", todos os valores cadastrados nos campos customizados estão na tabela **GUMGA_FLD_VLE**

SELECT * FROM GUMGA_FLD_VLE;

| ID   | OI   | DATEVALUE   | GUMGAMODELID   | LOGICVALUE   | NUMBERVALUE   | TEXTVALUE     | FIELD_ID   |
|------|------|-------------|----------------|--------------|---------------|---------------|------------|
| 1    | null | null        | 1              | null         | null          | Pessoa Física | 1          |
| 2    | null | null        | 1              | null         | 13.00         | null          | 2          |

O campo **FIELD_ID** mantém a referência para qual campo o registro pertence.

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
