package br.com.criptografiaJasypt.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.criptografiaJasypt.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GumgaCrudRepository<Usuario, String> {

    public Usuario findByLoginEquals(String login);
}