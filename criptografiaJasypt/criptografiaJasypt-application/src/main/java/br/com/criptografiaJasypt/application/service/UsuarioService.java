package br.com.criptografiaJasypt.application.service;

import io.gumga.application.GumgaService;

import io.gumga.application.service.JasyptGumgaPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.criptografiaJasypt.application.repository.UsuarioRepository;
import br.com.criptografiaJasypt.domain.model.Usuario;


@Service
@Transactional
public class UsuarioService extends GumgaService<Usuario, String> implements LoginService<Usuario> {

    private final static Logger LOG = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository repositoryUsuario;

    @Autowired
    private JasyptGumgaPasswordService encryptPassword;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.repositoryUsuario = repository;
    }

    @Override
    public Usuario save(Usuario resource) {
        resource.setSenha(encryptPassword.encryptPassword(resource.getSenha()));

        return super.save(resource);
    }

    public void befreSave(Usuario user){
        user.setSenha(encryptPassword.encryptPassword(user.getSenha()));
    }

    private Boolean verificaSenha(Usuario usuario, String senha){

            return encryptPassword.isPasswordCorrect(senha, usuario.getSenha());
    }

    public Object login(String login, String senha){
        Usuario usuario = repositoryUsuario.findByLoginEquals(login);
        if (usuario != null){
            if (verificaSenha(usuario, senha)){
                return usuario;
            }else{
                return "Senha Incorreta";
            }
        }else{
            return "Usuário não encontrado";
        }
    }

}