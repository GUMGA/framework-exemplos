package io.gumga.buscatextual.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import io.gumga.buscatextual.application.repository.EmpresaRepository;
import io.gumga.buscatextual.domain.model.Empresa;


@Service
@Transactional
public class EmpresaService extends GumgaService<Empresa, String> {

    private final static Logger LOG = LoggerFactory.getLogger(EmpresaService.class);
    private final EmpresaRepository repositoryEmpresa;

    @Autowired
    public EmpresaService(EmpresaRepository repository) {
        super(repository);
        this.repositoryEmpresa = repository;
    }

}