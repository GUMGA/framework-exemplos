package br.com.servicosCRUD.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.servicosCRUD.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, String> {}