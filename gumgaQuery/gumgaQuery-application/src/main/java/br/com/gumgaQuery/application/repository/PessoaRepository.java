package br.com.gumgaQuery.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaQuery.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}