# Criptografia com Jasypt

[![](https://avatars3.githubusercontent.com/u/13262049?s=200&v=4)](https://github.com/GUMGA/frameworkbackend)

Jasypt é uma biblioteca que permite que o desenvolvedor adicione uma camada básica de criptografia com o mínimio de esforço, e sem que seja necessário um profundo conhecimento sobre como funcionam algorítmos de criptografia.
Para saber mais sobre o Jasypt e suas peculiaridades, consulte o site oficial [clicando aqui](http://www.jasypt.org/)

O Framework Gumga implementa esta biblioteca e torna o uso de criptografia ainda mais simples. A principal motivação deste uso é o armazenamento de senhas no banco de dados, considerando não ser uma boa prática armazenar o valor "real" da mesmas em uma coluna.

Iremos demonstrar de maneira simples como trabalhar com persistência criptografada de senhas de usuário em um sistema.

### JasyptGumgaPasswordService

Esta é a principal classe de manipulação desta funcionalidade, ela implementa dois métodos *encryptPassword(...)* e *isPasswordCorrect(...)*, que fazem a criptografia de uma String, e verifica a corretude de uma senha criptografada respectivamente.
```java
private static StandardStringDigester encryptor;
{
  //Parâmetros da criptografia a ser utilizada
    encryptor = new StandardStringDigester();
    encryptor.setAlgorithm("SHA-1");
    encryptor.setIterations(10000);
}
@Override
public String encryptPassword(String rawPassword) {
    return encryptor.digest(rawPassword);
}
@Override
public Boolean isPasswordCorrect(String informedPassword, String encryptedPassword) {
    return encryptor.matches(informedPassword, encryptedPassword);
}
```

### Exemplo Prático

Criamos um projeto com uma entidade *Usuario* com alguns campos, nossa intenção é criar um sistema de login em que quando for válido, retorna um objeto contendo todos os dados do usuário, e nega caso algum dado de acesso (usuário ou senha) sejam inválidos.

Para isso precisamos de alguns campos:

```java
//...
@Column(name = "login", unique = true)
private String login;
@Column(name = "senha")
private String senha;
@Column(name = "nome")
private String nome;
@Column(name = "dataNasc")
private Date dataNasc;
@Columns(columns = {
@Column(name = "endereco_zip_code"),
@Column(name = "endereco_premisse_type"),
@Column(name = "endereco_premisse"),
@Column(name = "endereco_number"),
@Column(name = "endereco_information"),
@Column(name = "endereco_neighbourhood"),
@Column(name = "endereco_localization"),
@Column(name = "endereco_state"),
@Column(name = "endereco_country"),
@Column(name = "endereco_latitude"),
@Column(name = "endereco_longitude"),
@Column(name = "endereco_formal_code"),
@Column(name = "endereco_state_code")
})
private GumgaAddress endereco;
//...
```

Agora, sempre que cadastrarmos um novo usuário, precisamos tratar o campo *senha*, vamos então sobrescrever na classe de serviços o método *save(...)*
> Para saber mais sobre como manipular a criação de dados no Gumga Framework consulte a documentação sobre *Serviços CRUD*

```java
@Override
public Usuario save(Usuario resource) {
    resource.setSenha(encryptPassword.encryptPassword(resource.getSenha()));

    return super.save(resource);
}
```
Observe que, antes de "salvar" a entidade recebida, nós geramos uma String criptografada e passamos a mesma para o *resource* pelo método de acesso *setSenha(...)*
E isso gera no banco de dados um registro incapaz de ser interpretado. Vamos enviar uma requisição cadastrando um novo usuário:<br>
Método: POST<br>
Rota:
```
http://*servidor*/criptografiaJasypt-api/api/usuario
```
Body:
```json
{
	"login":"caito",
	"senha":"1234",
	"nome":"Caito Silva Sauro",
	"dataNasc":"1988-01-18",
	"endereco":{
	    	"zipCode": "87210028",
            "premisseType": "Casa",
            "premisse": "residencial",
            "number": "2004",
            "information": "Av. Mato Grosso",
            "neighbourhood": "Zona Norte",
            "localization": "Springfield",
            "state": "Pr",
            "country": "Brasil",
            "latitude": 26.5645,
            "longitude": 98.4560,
            "formalCode": "+55",
            "stateCode": "44"
	}

}
```
Observe que cadastramos a senha com o valor "1234", vejamos como isso se refletiu no banco de dados:<br>

SELECT LOGIN, NOME,SENHA FROM USUARIO;

| NOME   | LOGIN              | SENHA                                    |
|---------|-------------------|------------------------------------------|
| Caito Silva Sauro   | caito | DVnAkJ8ejsqukfM9opixXdcfaE6ZRlA7ZsdkPQ== |

Vamos salvar uma série de registros e verificar como se comportam os valores de "senha"

| NOME              | LOGIN   | SENHA                                    |
|-------------------|---------|------------------------------------------|
| Caito Silva Sauro | caito   | DVnAkJ8ejsqukfM9opixXdcfaE6ZRlA7ZsdkPQ== |
| William Da Costa  | douglas | sEuFsZjM2g5BOkmSm5FKHwhFuzzZfj5LfK3mBg== |
| Felipe Feliposo   | felipe  | 29HPdmvTBRQu/LL0XhT8pWE8DGixzU0WMNQWhg== |
| Mateus Matoso     | mateus  | 7gBkMd1NOAQXlPfVO4mF4h8mXo49ayC2S48HAw== |

Desta maneira não temos mais nenhum registro de senha que seja interpretável no banco de dados.

### Fazendo Login

Uma vez que temos nossos dados criptografados persistidos, precisamos agora fazer uso dos mesmos.
Criamos um sistema simples de login, que como descrito anteriormente vai apenas retornar o objeto "usuario" caso haja êxito.

A rota para acesso desse login na API é a seguinte:
```
http://*servidor*/criptografiaJasypt-api/api/usuario/login
```
O método recebe como parâmetro a chave *login* e *senha* que deve conter os respectivos valores. O método API que criamos é o seguinte:
```java
@RequestMapping("/login")
public ResponseEntity<Object> login(@RequestHeader String login, @RequestHeader String senha) {
    Object o = loginService.login(login, senha);
    if (o instanceof Usuario) {
        return ResponseEntity.status(200).body(o);
    }
    return ResponseEntity.status(404).body(o);
}
```
E o método *login(...)* é o serviço que cumpre com a função lógica, e está implementado na classe **UsuarioService**:
```java
/**
 * Método que faz a conferência da senha informada pelo usuário com
 * a senha criptografada registrada no banco
 * @param usuario
 * @param senha
 * @return
 */
private Boolean verificaSenha(Usuario usuario, String senha) {
    return encryptPassword.isPasswordCorrect(senha, usuario.getSenha());
}
/**
 * Este método recebe um usuário e uma senha e faz uma busca pelo login,
 * caso haja um usuário, ele faz a comparação das senhas
 * @param login
 * @param senha
 * @return
 */
public Object login(String login, String senha) {
    Usuario usuario = repositoryUsuario.findByLoginEquals(login);
    if (usuario != null) {
        if (verificaSenha(usuario, senha)) {
            return usuario;
        } else {
            return "Senha Incorreta";
        }
    } else {
        return "Usuário não encontrado";
    }
}
```
Podemos agora tentar fazer um login por requisição:
Método: GET<br>
Rota:
```
http://*servidor*/criptografiaJasypt-api/api/usuario/login
```
RequestHeader:

| Key   | Value |
|-------|-------|
| login | caito |
| senha | 1234  |

Como resultado, obtemos o seguinte conteúdo:
```json
{
    "id": "360DC7B18E2030B14BB0024287DB433A",
    "oi": null,
    "login": "caito",
    "senha": "DVnAkJ8ejsqukfM9opixXdcfaE6ZRlA7ZsdkPQ==",
    "nome": "Caito Silva Sauro",
    "dataNasc": "1988-01-18T00:00:00Z",
    "endereco": {
        "zipCode": "87210028",
        "premisseType": "Casa",
        "premisse": "residencial",
        "number": "2004",
        "information": "Av. Mato Grosso",
        "neighbourhood": "Zona Norte",
        "localization": "Springfield",
        "state": "Pr",
        "country": "Brasil",
        "latitude": 26.5645,
        "longitude": 98.456,
        "formalCode": "+55",
        "stateCode": "44"
    }
}
```

Do mesmo modo, se tentamos fazer a mesma requisição porém passando a senha como "123", recebemos como o resposta o seguinte:
```
"Senha Incorreta"
```
Experimente agora fazer outras buscas, cadastrando novos usuários e pesquisando registros inexistentes e com senhas erradas e veja o resultado!


License
----

LGPL-3.0


**Free Software, Hell Yeah!**
