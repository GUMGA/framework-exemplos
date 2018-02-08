package br.com.gumgaModel.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaModel.domain.model.PessoaSharedSeqId;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSharedSeqIdRepository extends GumgaCrudRepository<PessoaSharedSeqId, String> {}