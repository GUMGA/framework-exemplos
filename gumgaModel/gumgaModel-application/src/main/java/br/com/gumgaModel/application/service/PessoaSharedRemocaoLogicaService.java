package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaSharedRemocaoLogicaRepository;
import br.com.gumgaModel.domain.model.PessoaSharedRemocaoLogica;


@Service
@Transactional
public class PessoaSharedRemocaoLogicaService extends GumgaService<PessoaSharedRemocaoLogica, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaSharedRemocaoLogicaService.class);
    private final PessoaSharedRemocaoLogicaRepository repositoryPessoaSharedRemocaoLogica;

    @Autowired
    public PessoaSharedRemocaoLogicaService(PessoaSharedRemocaoLogicaRepository repository) {
        super(repository);
        this.repositoryPessoaSharedRemocaoLogica = repository;
    }

    public boolean hasData(){
        return repositoryPessoaSharedRemocaoLogica.count() > 0;
    }

}