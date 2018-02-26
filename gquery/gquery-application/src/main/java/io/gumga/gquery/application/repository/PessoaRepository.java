package io.gumga.gquery.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import io.gumga.gquery.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}