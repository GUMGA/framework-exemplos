package br.com.customFields.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.customFields.application.repository.PessoaRepository;
import br.com.customFields.domain.model.Pessoa;


@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repositoryPessoa;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
        this.repositoryPessoa = repository;
    }

}