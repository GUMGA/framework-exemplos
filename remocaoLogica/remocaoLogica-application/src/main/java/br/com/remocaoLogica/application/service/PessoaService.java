package br.com.remocaoLogica.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.QueryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.remocaoLogica.application.repository.PessoaRepository;
import br.com.remocaoLogica.domain.model.Pessoa;

import java.util.List;


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

    @Override
    public void delete(Pessoa resource) {
        System.out.println("teste");
        super.delete(resource);
    }

    public List<Pessoa> getLixeira(){
        QueryObject aq = new QueryObject();
        aq.setAq(" ");
        return repositoryPessoa.search(aq).getValues();
    }

    public boolean hasData(){
        return repositoryPessoa.count() > 0;
    }

}