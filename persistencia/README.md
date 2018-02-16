# Persistência de Dados
---

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Vamos abordar nesse exemplo prático como podemos criar e manipular registros em bancos de dados externos.<br>
Por padrão o Gumga Framework implementa o Hibernate, uma ferramenta consolidada para a manipulação de bancos de dados, que fornece uma série de funcionalidades que visam facilitar esse trabalho.<br>
Antes de qualquer coisa, vamos entender mais sobre as entidades envolvidas.



#### Entidades Responsáveis
O Framework Gumga dispõe de classes para fornecer a configuração nos principais bancos de dados, são eles:

* [H2](http://www.h2database.com/html/main.html)
* [MySQL](https://www.mysql.com/)
* [PostGreSQL](https://www.postgresql.org/)
* [Oracle](https://www.oracle.com/br/database/index.html)

Porém, por utilizar Hibernate, nada impede a implementação de qualquer outro banco de dados que se queira fazer uso.<br>




###### *DataSourceProvider*
Esta é a interface que define o comportamento de uma fonte de dados, ela fornece os metodos para criação de banco de dados *(createDataSource(...))*. Isso obriga com que as classes de configuração que implementam essa interface tenham os métodos que obtém os dados de conexão.

###### *DatabaseConfigSupport*
Classe para manipulação dos *DataSourceProvider*<br>
Ela cria um mapeamento para as configurações de *H2, MYSQL, POSTGRES, ORACLE*.<br>
também define padrões para o Hibernate.

#### Configurações
Considerando que o Framework implementa as peculiaridades de implementação do banco de dados e que todo acesso a dados das entidades persistidas podem ser acessadas pelas entidades Repository e Service, todo o procedimento de manipulação do banco de dados por padrão é transparente ao usuário, bastando apenas que o arquivo de propriedades do sistema contenha as informações de conexão ao banco (user, password, url, etc.)<br>
<br>
Quando o projeto é inicialmente criado pelo Gerador GG, é oferecido ao usuário a opção de configurar o banco de dados externo (caso não seja definido, o projeto irá utilizar o H2). Abaixo segue o exemplo da geração do projeto com MySQL:  

```
? Nome do grupo..:  br.com
? Versão..:  1.0.0
? Como desejar gerar seu front-end? 1 - Não gerar front-end
✔ O seu incrível projeto(persistencia) foi gerado.
? Você deseja configurar um banco de dados? Yes
? Qual banco de dados você utiliza? 1 - MySQL
? Endereço do servidor..:  localhost
? Porta..:  3306
? Nome da base de dados..:  persistencia
? Usuário do banco..:  root
? Senha..:  qwe123
? Você utiliza o segurança? No
```
Como se pode ver, definimos o banco de dados com as seguintes configurações:
```
Endereço: localhost
Porta: 3306
Nome da base de dados: persistencia
Usuário: root
Senha: qwe123
```
Essas opções são salvas no arquivo de propriedades do sistema, no diretório **gumgafiles** na raiz da pasta do usuário, fornecendo variáveis de ambiente para acesso ao banco de dados.
> O Diretorio deste arquivo é gerado automaticamente pelo gerador, **/user.home/gumgafiles/nomeDaAplicacao.properties**

Segue o conteúdo padrão desse arquivo:
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
hibernate.dialect = org.hibernate.dialect.MySQL5Dialect


#---------------------------------------------------------------------------
#Datasource
name = MYSQL
dataSource.className = com.mysql.jdbc.jdbc2.optional.MysqlDataSource
dataSource.url = jdbc:mysql://localhost:3306/persistencia?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=utf8&createDatabaseIfNotExist=true
dataSource.user = root
dataSource.password = qwe123

#---------------------------------------------------------------------------
#Segurança
url.host = GUMGA_SECURITY
security.token = eterno
```

Observe que, os dados que informamos na criação do projeto estão contidas neste documento juntamente com as configurações de variáveis externas do Hibernate.
Quando quisermos modificar algum dado de acesso ao banco de dados, basta substituir os valores deste documento. Mas antes vamos entender mais um pouco de como podemos manipular essa conexão.


###### *JpaConfiguration*
Esta classe é responsável por definir as configurações do JPA, ela faz a leitura do arquivo de propriedades e busca os parâmetros para a implementação do Hibernate. Veja abaixo os métodos responsáveis pela leitura dessas informações:
```Java
//...//
private Properties commonProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.ejb.naming_strategy",getProperties().getProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.EJB3NamingStrategy"));
    properties.setProperty("hibernate.show_sql", getProperties().getProperty("hibernate.show_sql", "false"));
    properties.setProperty("hibernate.format_sql", getProperties().getProperty("hibernate.format_sql", "false"));
    properties.setProperty("hibernate.connection.charSet", getProperties().getProperty("hibernate.connection.charSet", "UTF-8"));
    properties.setProperty("hibernate.connection.characterEncoding", getProperties().getProperty("hibernate.connection.characterEncoding", "UTF-8"));
    properties.setProperty("hibernate.connection.useUnicode", getProperties().getProperty("hibernate.connection.useUnicode", "true"));
    properties.setProperty("hibernate.jdbc.batch_size", getProperties().getProperty("hibernate.jdbc.batch_size", "50"));
    properties.setProperty("hibernate.dialect", getProperties().getProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect"));

    properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("liquibase.enabled", "false");
//        properties.put("liquibase.drop-first","false");
//        properties.put("liquibase.change-log","src/main/resources/liquibase/changelog-master.xml");

    return properties;
}

private Properties getProperties() {
    if(this.gumgaValues == null)
        this.gumgaValues = new ApplicationConstants();

    if(this.properties == null)
        this.properties = this.gumgaValues.getCustomFileProperties();

    return this.properties;
}
//...//
```
Observe que, o método **commonProperties()** busca as variáveis no arquivo de propriedades (a partir do método getProperties()) e no caso de não encontrar nenhum parâmetro válido, ele retorna um valor **default**
> A configuração default seta a JPA para trabalhar com H2, isso é um artifício para simplificar a implementação de um projeto sem banco de dados externos

 O parâmetro **hibernate.hbm2ddl.auto** define o comportamento da aplicação quanto a um banco de dados existente, existem outros paradigmas como *validate, create, create-drop* (leia mais na documentação oficial do Hibernate)  porém **cuidado, a modificação desses parâmetros pode causar perdas de dados persistidos!**

 #### Exemplos de Utilização

 O projeto de exemplo possui uma modelagem de Produto e Carrinho com campos bem simples e alguns seeds para a visualização da modificação dos bancos de dados.
 > Caso tenha dúvidas a respeito de como criar e manipular entidades de modelagem ou seeds, veja os exemplos específicos na documentação do Gumga Framework
