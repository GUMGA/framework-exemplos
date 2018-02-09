package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaSharedAleatIdRepository;
import br.com.gumgaModel.domain.model.PessoaSharedAleatId;


@Service
@Transactional
public class PessoaSharedAleatIdService extends GumgaService<PessoaSharedAleatId, String> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaSharedAleatIdService.class);
    private final PessoaSharedAleatIdRepository repositoryPessoaSharedAleatId;

    @Autowired
    public PessoaSharedAleatIdService(PessoaSharedAleatIdRepository repository) {
        super(repository);
        this.repositoryPessoaSharedAleatId = repository;
    }

    public boolean hasData(){
        return repositoryPessoaSharedAleatId.count() > 0;
    }

}