package br.com.tenancy.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.GumgaThreadScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.tenancy.application.repository.ProdutoRepository;
import br.com.tenancy.domain.model.Produto;


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

    @Override
    public Produto save(Produto resource) {
        resource.setPrecoVenda((Math.ceil(resource.getPrecoCusto() * ((resource.getMargemLucro() / 100.0) + 1.00))) + 0.90);
        return super.save(resource);
    }

    public boolean hasData(){
        return repositoryProduto.count() > 0;
    }
}