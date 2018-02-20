package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaRemocaoLogicaRepository;
import br.com.gumgaModel.domain.model.PessoaRemocaoLogica;


@Service
@Transactional
public class PessoaRemocaoLogicaService extends GumgaService<PessoaRemocaoLogica, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaRemocaoLogicaService.class);
    private final PessoaRemocaoLogicaRepository repositoryPessoaRemocaoLogica;

    @Autowired
    public PessoaRemocaoLogicaService(PessoaRemocaoLogicaRepository repository) {
        super(repository);
        this.repositoryPessoaRemocaoLogica = repository;
    }

    public boolean hasData(){
        return repositoryPessoaRemocaoLogica.count() > 0;
    }

}