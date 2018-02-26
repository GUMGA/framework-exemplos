package io.gumga.gquery.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import io.gumga.gquery.domain.model.Endereco;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends GumgaCrudRepository<Endereco, String> {}