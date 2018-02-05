# GG Gerador

[![](httpsa://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)
#### Este exemplo se refere ao gerador de projetos gg.
Esta ferramenta tem como funcionalidade facilitar a criação de novos projetos utilizando o *Gumga Framework*
Ela gera uma estrutura de arquitetura completa com funcionalidades CRUD e persistência, além de interface web funcional.
> Este guia é baseado no sistema operacional Linux Ubuntu
## Instalação
#### Pré-requisitos
* [ *Java 8*](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html)
* [ *Maven 3*](https://maven.apache.org/)
* [ *Bower*](https://bower.io/)
* [ *npm*](https://npmjs.com/)

#### 1. Instale o cli 
No terminal, execute a seguinte linha de comando:
(talvez você precise de permissões de administrador)
``` 
npm i -g --unsafe-perm gumga-cli @angular/cli
```
> Certifique-se de ter instalado corretamente os pré requisitos em modo global
> Este comando irá fazer o download e instalação do **gumga cli** a partir do repositório npm

#### 2. Iniciar uma aplicação
Para confirmar que o aplicativo foi instalado com sucesso, digite "gg" no terminal, e a seguinte mensagem deverá ser apresentada:


```
gg 2.3.0 
     
   USAGE

     gg <command> [options]

   COMMANDS

     init [artifactId] [groupId] [version]      Crie um novo projeto               
     build [run] [module]                       Faz a compilação dos módulos       
     entity [entityName]                        Gera uma nova entidade             
     service [entityName]                       Gera uma service                   
     api [entityName]                           Gera uma API                       
     presentation [entityName]                  Gera o front-end                   
     run [module]                               Inicia o back-end e front-end      
     help <command>                             Display help for a specific command

   GLOBAL OPTIONS

     -h, --help         Display help                                      
     -V, --version      Display version                                   
     --no-color         Disable colors                                    
     --quiet            Quiet mode - only displays warn and error messages
     -v, --verbose      Verbose mode - will also output debug messages    
```
Uma vez instalado com sucesso, podemos começar a usar o gerador
Digite no terminal:
```
gg init novoProjeto
```


Este comando cria uma nova aplicação a partir de um modelo pronto
O gerador irá guiá-lo entre as configurações do projeto como:
* **Nome do grupo**
* **Versão**
* **Tecnologia de front-end**
    >você pode optar por não gerar nenhuma estrutura de front-end também!
* **Bando de dados**
    >caso não queira configurar um sistema de persistência específico, seu projeto será configurado para trabalhar com [H2 Database Engine](http://www.h2database.com/html/main.html)

Perceba que, uma pasta foi criada no diretório do terminal com o nome do seu projeto

#### 3. Compilando Seu Projeto
Ainda no terminal, navegue até a pasta gerada
```
cd novoProjeto
gg build run
```
>o comando "gg build run" irá baixar todas as dependências necessárias e fazer a compilação do projeto

Pronto, você já tem um modelo funcional utilizando o Gumga Framework!
Podemos prosseguir criando as entidades, mas primeiro vamos entender um mais sobre a arquitetura que geramos

#### Estrutura do projeto
O Gumga Framework é composto por várias camadas, cada uma com uma responsabilidade dentro do sistema
Observe que estas camadas estão bem divididas em pacotes dentro do diretório do sistema que geramos

* **API**
Nesta camada estão todos os métodos que serão invocados pelo front end via HTTP requests. Estes métodos são mapeados como serviços REST (RESTful Web Services).

* **Application**
Esta camada contém as classes Repositories e Services responsáveis pela persistência e regras de negócios respectivamente.

* **Boot**
Este módulo é o responsável em te dar a possibilidade de executar o projeto com SpringBoot.

* **Configuration**
Camada responsável por compartilhar configurações do projeto ente as camadas Boot e Infrastructure.

* **Domain**
A camada domain compreende as entidades do sistema e é nela que configuramos os mapeamentos JPA. Lembrando que o framework de persistência utilizado é o Hibernate. Porém devemos ressaltar que NÃO é nesta camada que configuramos o persistence.xml

* **Gateway**
Esta é uma camada opcional e é utilizada quando os dados recebidos pelas chamadas as API’s precisam ser tratados ou transformados antes de seguirem para a camada Service. Muito útil quando se utiliza o padrão DTO (Data Transfer Object).

* **Infrastructure**
Este módulo é o responsável em te dar a possibilidade de executar o projeto sem SpringBoot.

* **Presentation**
Esta é a camada que possui todo o front-end do projeto.

>Você pode adicionar o archetype da Gumga na IDE de sua preferência.
[IntelliJ IDEA](https://www.jetbrains.com/help/idea/2016.3/add-archetype-dialog.html)
[Eclipse](http://howtodoinjava.com/tools/eclipse/how-to-import-maven-remote-archetype-catalogs-in-eclipse/)


#### Entidade

A camada domain compreende as entidades do sistema e é nela que configuramos os mapeamentos JPA. Lembrando que o framework de persistência utilizado é o Hibernate. Porém devemos ressaltar que **não** é nesta camada que configuramos o persistence.xml
Segue um exemplo da estrutura de uma entidade gerada
Classe "Pessoa" com os atributos "nome" e "idade"
```java
package br.com.novoProjeto.domain.model;
//imports

@GumgaMultitenancy
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="PessoaTYPE")
@Entity(name = "Pessoa")
@Table(name = "Pessoa", indexes = {
    @Index(name = "Pessoa_gum_oi", columnList = "oi")
})
public class Pessoa extends GumgaModelUUID {


    @Column(name = "nome")
	private String nome;
    @Column(name = "idade")
	private Integer idade;

    public Pessoa() {}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return this.idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
}
```
### 4. Gerando uma Entidade (classe)
No terminal, navegue até a pasta raiz do seu projeto e entre com o seguinte comando:
```
gg entity
```
O gerador disponibiliza um guia pelas opções 
> Atente-se às opções solicitadas,  neste momento será definido se sua classe será herdada por outra entidade, serão criados os campos e associações
Este comando oferece a opção de criar as classes e interfaces nas outras camadas (Application, API e Presentation (front-end)) 

#### Aplicação
Esta camada contém as classes Repositories e Services responsáveis pela persistência e regras de negócios respectivamente.

Exemplo de interface Repository. 
```java
package br.com.novoProjeto.application.repository;

//import

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}
```

>Interfaces são utilizadas ao invés de classes devido ao Spring Data, framework responsável pela a persistência.

Exemplo de interface Service. 
```java
package br.com.novoProjeto.application.service;

//import

@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, String> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repositoryPessoa;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
        this.repositoryPessoa = repository;
    }
}
```

#### Gerando uma camada de Aplicação
No terminal, navegue até a pasta raiz do seu projeto e entre com o seguinte comando:
```
gg service
```
O gerador disponibiliza um guia pelas opções 
> Observe que, uma vez que você tenha gerado as entidades de domínio pelo "gg entity", e selecionado a opção de criação para as camadas, este módulo já estará criado, tornando a criação do mesmo desnecessário

### API
Nesta camada estão todos os métodos que serão invocados pelo front end via HTTP requests. Estes métodos são mapeados como serviços REST (RESTful Web Services).
Portanto, segue um exemplo de classe API:
```java
package br.com.novoProjeto.api;

//import

@RestController
@RequestMapping("/api/pessoa")
@Transactional
public class PessoaAPI extends GumgaAPI<Pessoa, String> {
    @Autowired
    public PessoaAPI(GumgaService<Pessoa, String> service) {
        super(service);
    }
}
```

#### Gerando uma camada API
No terminal, navegue até a pasta raiz do seu projeto e entre com o seguinte comando:
```
gg api
```
O gerador disponibiliza um guia pelas opções 
> Observe que, uma vez que você tenha gerado as entidades de domínio pelo "gg entity", e selecionado a opção de criação para as camadas, este módulo já estará criado, tornando a criação do mesmo desnecessário

#### Apresentação
Esta camada contém toda o front-end da aplicação, módulos JavaScript, HTML e CSS
##### Gerando uma camada de Apresentação
No terminal, navegue até a pasta raiz do seu projeto e entre com o seguinte comando:
```
gg presentation
```
O gerador disponibiliza um guia pelas opções 
> Observe que, uma vez que você tenha gerado as entidades de domínio pelo "gg entity", e selecionado a opção de criação para as camadas, este módulo já estará criado, tornando a criação do mesmo desnecessário


### Infrastructure

O objetivo desta camada é concentrar as configurações da aplicação, tais como Persistência, Spring e Aplicação web. Por padrão o framework gera a classe br.minha.aplicacao.infrastructure.config.Application contendo configurações suficientes para rodar a aplicação sem nenhuma alteração, utilizando o banco de dados H2 em memória.


### Gateway

Esta é uma camada opcional e é utilizada quando os dados recebidos pelas chamadas as API’s precisam ser tratados ou transformados antes de seguirem para a camada Service. Muito útil quando se utiliza o padrão DTO (Data Transfer Object).

### Aprendi a gerar um projeto, e agora?
Se você gerou o projeto e chegou até aqui, provavelmente está se perguntando qual é a próxima etapa.
Se o seu foco agora for desenvolver o frontend, você pode optar por ler a documentação dos componentes, [clicando aqui](https://gumga.github.io/#/app/component)
Agora, se você optar por aprender mais sobre o que o framework oferece, sobre o desenvolvimento backend, [clique aqui](https://gumga.github.io/#/app/framework) e veja a documentação do framework.

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
