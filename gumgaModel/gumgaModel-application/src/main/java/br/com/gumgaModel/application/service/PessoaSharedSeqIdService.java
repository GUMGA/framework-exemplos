package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaSharedSeqIdRepository;
import br.com.gumgaModel.domain.model.PessoaSharedSeqId;


@Service
@Transactional
public class PessoaSharedSeqIdService extends GumgaService<PessoaSharedSeqId, String> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaSharedSeqIdService.class);
    private final PessoaSharedSeqIdRepository repositoryPessoaSharedSeqId;

    @Autowired
    public PessoaSharedSeqIdService(PessoaSharedSeqIdRepository repository) {
        super(repository);
        this.repositoryPessoaSharedSeqId = repository;
    }

}