package br.com.customFields.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.customFields.application.repository.ContaRepository;
import br.com.customFields.domain.model.Conta;


@Service
@Transactional
public class ContaService extends GumgaService<Conta, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(ContaService.class);
    private final ContaRepository repositoryConta;

    @Autowired
    public ContaService(ContaRepository repository) {
        super(repository);
        this.repositoryConta = repository;
    }

}