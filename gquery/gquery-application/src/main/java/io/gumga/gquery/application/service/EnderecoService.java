package io.gumga.gquery.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import io.gumga.gquery.application.repository.EnderecoRepository;
import io.gumga.gquery.domain.model.Endereco;


@Service
@Transactional
public class EnderecoService extends GumgaService<Endereco, String> {

    private final static Logger LOG = LoggerFactory.getLogger(EnderecoService.class);
    private final EnderecoRepository repositoryEndereco;

    @Autowired
    public EnderecoService(EnderecoRepository repository) {
        super(repository);
        this.repositoryEndereco = repository;
    }

}