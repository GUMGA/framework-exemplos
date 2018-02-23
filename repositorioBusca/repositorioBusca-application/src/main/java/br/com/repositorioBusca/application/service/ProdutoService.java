package br.com.repositorioBusca.application.service;

import br.com.repositorioBusca.domain.model.QProduto;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAQueryFactory;
import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.repositorioBusca.application.repository.ProdutoRepository;
import br.com.repositorioBusca.domain.model.Produto;

import java.util.List;


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

    public boolean hasData() {
        return repositoryProduto.count() > 0;
    }

    @Override
    public Produto save(Produto resource) {
        resource.setPrecoVenda(Math.ceil(resource.getPrecoCusto() * ((resource.getMargemLucro() / 100.0) + 1)) + 0.99);
        resource.setValorFrete(Math.ceil(resource.getFatorFrete() * resource.getPeso()));
        return super.save(resource);
    }

    public List<Produto> getListGTProdutoPrecoVenda(Double param) {
        return repositoryProduto.findAll(QProduto.produto.precoVenda.gt(param));
    }
    public List<Produto> getListLOEProdutoMargem(Double param) {
        return repositoryProduto.findAll(QProduto.produto.margemLucro.loe(param));
    }


}
