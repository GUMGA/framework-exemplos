package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaSequeciaIdRepository;
import br.com.gumgaModel.domain.model.PessoaSequeciaId;


@Service
@Transactional
public class PessoaSequeciaIdService extends GumgaService<PessoaSequeciaId, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaSequeciaIdService.class);
    private final PessoaSequeciaIdRepository repositoryPessoaSequeciaId;

    @Autowired
    public PessoaSequeciaIdService(PessoaSequeciaIdRepository repository) {
        super(repository);
        this.repositoryPessoaSequeciaId = repository;
    }

    public boolean hasData() {
        return repositoryPessoaSequeciaId.count() > 0;
    }
}