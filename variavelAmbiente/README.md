# Variáveis de Ambiente
---

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

No desenvolvimento de um projeto usando o Gumga Framework é possível utilizar os manipuladores de variáveis de ambiente do Java e Spring, porém temos algumas ferramentas que visam facilitar esse trabalho.</br>
Neste exemplo iremos demonstrar o funcionamento delas.

#### Arquivo Customizável de Propriedades
###### *Custom File Properties*
Por padrão, todo projeto Gumga Framework que utiliza banco de dados (que não seja embutido) ou faça chamadas externas ao [Segurança](https://gumga.io/security/), depende de um arquivo de propriedades para a configuração de parâmetros do sistema.
Na prática é um arquivo de texto simples com extensão **.properties**

```
#---------------------------------------------------------------------------
#Hibernate

hibernate.ejb.naming_strategy= org.hibernate.cfg.EJB3NamingStrategy
hibernate.show_sql = false
hibernate.format_sql = false
hibernate.connection.charSet = UTF-8
hibernate.connection.characterEncoding = UTF-8
hibernate.connection.useUnicode = true
hibernate.jdbc.batch_size = 50
hibernate.hbm2ddl.auto = update
#hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
#hibernate.dialect = org.hibernate.dialect.Oracle10gDialect
#hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
#hibernate.dialect = org.hibernate.dialect.H2Dialect

#---------------------------------------------------------------------------
#Datasource
#name = MYSQL
#dataSource.className = com.mysql.jdbc.jdbc2.optional.MysqlDataSource
#dataSource.url = jdbc:mysql://ip da dabase de dados:3306/nomeDatabase?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=utf8
#dataSource.user =
#dataSource.password =

#name = ORACLE
#dataSource.className = oracle.jdbc.pool.OracleDataSource
#dataSource.url = jdbc:oracle:thin:@ip da dabase de dados:1521:orcl
#dataSource.user =
#dataSource.password =

#name= POSTGRES
#dataSource.className=org.postgresql.jdbc2.optional.SimpleDataSource
#dataSource.url=jdbc:postgresql://ip da dabase de dados:5433/nomedatabase
#dataSource.user=
#dataSource.password=

#name= H2
#dataSource.className=org.h2.jdbcx.JdbcDataSource
#dataSource.url=jdbc:h2:mem:studio;MVCC=true
#dataSource.user=
#dataSource.password=


#---------------------------------------------------------------------------
#Segurança

#url.host = http://ip do segurança
#security.token = token longo da aplicação
```
Este é o arquivo padrão implementado pelo Framework, como se pode ver já encontramos nele um modelo de configuração básica para o funcionamento do sistema. São informações para o funcionamento do Hibernate, conexão com banco de dados (modelo para MySQL, Oracle, PostGreSQL e H2) e endereço de acesso ao Segurança, configurações para envio de email, etc.

Este arquivo fica alocado por padrão na pasta *"gumgafiles"* que é criada na raiz da pasta do usuário do sistema, e possui o nome do projeto (caso tenha sido gerado automaticamente)<br>
No Linux Ubuntu por exemplo, o diretório é o seguinte:
```
/home/*usuario*/gumgafiles/*arquivo*.properties
```
O acesso a essas informações dentro do projeto é feito por um método implementado na interface **GumgaValues.java**
```Java
default Properties getCustomFileProperties() {
    Properties toReturn = new Properties();
    try {
        InputStream input = new FileInputStream(System.getProperty("user.home") + "/gumgafiles/" + getCustomPropertiesFileName());
        toReturn.load(input);
    } catch (IOException e) {
        log.info("Utilizando properties padrão");
    }
    return toReturn;
}
```
Este método faz a leitura do arquivo e retorna um objeto **Properties** contendo os dados encontrados.
> Considerando que este método pertence a uma interface, nada impede que este método seja sobrescrito de acordo com a necessidade

No nosso projeto de exemplo, criamos algumas rotas para visualização dessas informações
na seguinte rota iremos visualizar um arquivo de propriedades no diretório padrão de uma aplicação Gumga
```
http://*servidor*/variavelAmbiente-api/api/pessoa/fileproperties
```

Uma requisição GET nessa rota nos retorna o seguinte:
```json
{
    "Arquivo_de_Propriedades": "Pasta do usuario",
    "Diretorio": "user.home/gumgafiles/variavelAmbiente.properties"
}
```
> Detalhe importante: <br>
Para reproduzir essa resposta neste projeto, você deve criar um arquivo com o nome "variavelAmbiente.properties" dentro de uma pasta chamada "gumgafiles" na raiz da pasta do usuário, contendo as informações desejadas.<br>
Você pode copiar as informações Json acima ou criar suas propriedades!

##### Arquivo de propriedades Local
Vimos como funciona a leitura de um arquivo de propriedades do diretório padrão, porém nada impede que você crie e manipule arquivos de propriedades no diretório que desejar, neste exemplo adicionamos um outro arquivo na pasta do projeto, podemos visualizá-lo na seguinte rota:
```
http://*servidor*/variavelAmbiente-api/api/pessoa/filepropertieslocal
```
O conteúdo deste arquivo em resposta do servidor é o seguinte:
```Json
{
    "Arquivo_de_propriedades": "Local",
    "Diretorio": "framework-exemplos/variavelAmbiente"
}
```
O acesso a este arquivo é feito por um método que invoca as ferramentas de leitura do Java e Spring
```Java
public Properties getCustomLocalFileProperties() {
    Properties toReturn = new Properties();
    try {
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "/" + getCustomPropertiesFileName());
        toReturn.load(input);
    } catch (IOException e) {
    }
    return toReturn;
}
```
Observe que lemos a propriedade "user.dir" a partir da classe System, que fornece uma série de variáveis de ambiente do sistema. Você pode ler uma série de dados do usuário, sistema e Ambiente Java a partir desta classe, como exemplo criamos duas rotas que fornecem algumas dessas informações:
```
Rota 1
http://*servidor*/variavelAmbiente-api/api/pessoa/system
Rota 2
http://*servidor*/variavelAmbiente-api/api/pessoa/getProp
```
> Experimente fazer uma requisição GET nestas rotas e observe a resposta.

#### Application Properties
Outro modo de definir variáveis de ambiente dentro de um projeto Gumga Framework, é utilizar o arquivo **application.yml** que fica no diretório *boot* do projeto.<br>
O diretório deste arquivo no nosso exemplo é o seguinte:
```
*/variavelAmbiente/variavelAmbiente-boot/src/main/resources/application.yml
```
Como se pode observar, as variáveis são definidas no formato padrão **yml** (não em json como definido no arquivo de propriedades)<br>
Veja abaixo o exemplo implementado:
```yml
server:
  context-path: /variavelAmbiente-api
variavel:
  var1: Variáveis lidas com sucesso!
  var2: Aqui você pode definir Valores e variáveis para acesso global no sistema
  var3: Diretório do arquivo applicaton.yml
  var4: /user.home/framework-exemplos/variavelAmbiente/variavelAmbiente-boot/src/main/resources/application.yml
```
Criamos também uma rota para acesso a esses dados, você pode visualizar na seguinte rota:
```
http://*servidor*/variavelAmbiente-api/api/pessoa/enviroment
```
A leitura desses dados é feita pela classe Enviroment do Spring que recebe uma String contendo a variável solicitada, e retorna o valor encontrados
```Java
//...//

@Autowired
org.springframework.core.env.Environment ambiente;

//...//

public List<String> getEnviroment() {
    List<String> vars = new ArrayList<>();
    vars.add(ambiente.getProperty("variavel.var1"));
    vars.add(ambiente.getProperty("variavel.var2"));
    vars.add(ambiente.getProperty("variavel.var3"));
    vars.add(ambiente.getProperty("variavel.var4"));
    return vars;
}
```
Nesse exemplo, estamos populando uma lista com os valores retornados.

---
License
----

LGPL-3.0


**Free Software, Hell Yeah!**
