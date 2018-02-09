package br.com.gumgaModel.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaModel.domain.model.PessoaSharedAleatId;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSharedAleatIdRepository extends GumgaCrudRepository<PessoaSharedAleatId, String> {}