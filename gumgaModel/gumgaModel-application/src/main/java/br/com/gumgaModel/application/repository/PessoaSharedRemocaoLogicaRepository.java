package br.com.gumgaModel.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaModel.domain.model.PessoaSharedRemocaoLogica;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSharedRemocaoLogicaRepository extends GumgaCrudRepository<PessoaSharedRemocaoLogica, Long> {}