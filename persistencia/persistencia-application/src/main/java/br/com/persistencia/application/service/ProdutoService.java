package br.com.persistencia.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.persistencia.application.repository.ProdutoRepository;
import br.com.persistencia.domain.model.Produto;


@Service
@Transactional
public class ProdutoService extends GumgaService<Produto, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoRepository repositoryProduto;

    @Autowired
    public ProdutoService(ProdutoRepository repository) {
        super(repository);
        this.repositoryProduto = repository;
    }

    public boolean hasData(){
        return repositoryProduto.count() > 0;
    }

}