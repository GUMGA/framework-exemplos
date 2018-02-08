package br.com.gumgaModel.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaModel.domain.model.PessoaAleatorioId;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaAleatorioIdRepository extends GumgaCrudRepository<PessoaAleatorioId, String> {}