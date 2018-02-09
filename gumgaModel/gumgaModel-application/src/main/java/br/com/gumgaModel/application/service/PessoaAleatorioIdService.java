package br.com.gumgaModel.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.gumgaModel.application.repository.PessoaAleatorioIdRepository;
import br.com.gumgaModel.domain.model.PessoaAleatorioId;


@Service
@Transactional
public class PessoaAleatorioIdService extends GumgaService<PessoaAleatorioId, String> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaAleatorioIdService.class);
    private final PessoaAleatorioIdRepository repositoryPessoaAleatorioId;

    @Autowired
    public PessoaAleatorioIdService(PessoaAleatorioIdRepository repository) {
        super(repository);
        this.repositoryPessoaAleatorioId = repository;
    }
    public boolean hasData(){
        return repositoryPessoaAleatorioId.count() > 0;
    }

}