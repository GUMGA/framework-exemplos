package br.com.novoProjeto.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.novoProjeto.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}