package br.com.informacoesDoUsuario.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.informacoesDoUsuario.domain.model.Livro;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends GumgaCrudRepository<Livro, String> {


}