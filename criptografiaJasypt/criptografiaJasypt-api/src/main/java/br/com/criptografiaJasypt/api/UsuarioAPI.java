package br.com.criptografiaJasypt.api;

import br.com.criptografiaJasypt.application.service.LoginService;
import br.com.criptografiaJasypt.domain.model.Usuario;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/usuario")
@Transactional
public class UsuarioAPI extends GumgaAPI<Usuario, String> {

    private final LoginService<Usuario> loginService;

    @Autowired
    public UsuarioAPI(@Qualifier("usuarioService") GumgaService<Usuario, String> service,
                      LoginService<Usuario> loginService) {
        super(service);
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader String login, @RequestHeader String senha) {
        Object o = loginService.login(login, senha);
        if (o instanceof Usuario) {
            return ResponseEntity.status(200).body(o);
        }
        return ResponseEntity.status(404).body(o);
    }

}