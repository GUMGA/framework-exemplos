package br.com.gumgaModel.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaModel.domain.model.PessoaSequeciaId;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSequeciaIdRepository extends GumgaCrudRepository<PessoaSequeciaId, Long> {}