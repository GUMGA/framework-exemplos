package io.gumga.buscatextual.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import io.gumga.buscatextual.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}