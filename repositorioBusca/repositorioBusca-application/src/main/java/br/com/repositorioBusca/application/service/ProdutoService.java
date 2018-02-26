package br.com.repositorioBusca.application.service;

import br.com.repositorioBusca.domain.model.QProduto;
import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.com.repositorioBusca.application.repository.ProdutoRepositoryQueryDSL;

import br.com.repositorioBusca.domain.model.Produto;

import java.util.List;


/**
 * Repositório de acesso ao banco de dados utilizando o QueryDSL
 */
@Service
@Transactional
public class ProdutoService extends GumgaService<Produto, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoRepositoryQueryDSL repositoryQueryDSL;

    @Autowired
    public ProdutoService(ProdutoRepositoryQueryDSL repository) {
        super(repository);
        this.repositoryQueryDSL = repository;
    }

    public boolean hasData() {
        return repositoryQueryDSL.count() > 0;
    }

    @Override
    public Produto save(Produto resource) {
        resource.setPrecoVenda(Math.ceil(resource.getPrecoCusto() * ((resource.getMargemLucro() / 100.0) + 1)) + 0.99);
        resource.setValorFrete(Math.ceil(resource.getFatorFrete() * resource.getPeso()));
        return super.save(resource);
    }

    public List<Produto> getListGTProdutoPrecoVenda(Double param) {
        return repositoryQueryDSL.findAll(QProduto.produto.precoVenda.gt(param));
    }

    public List<Produto> getListLOEProdutoMargem(Double param) {
        return repositoryQueryDSL.findAll(QProduto.produto.margemLucro.loe(param));
    }

    public List<Produto> getListProdutoNomeContains(String param) {
        return repositoryQueryDSL.findAll(QProduto.produto.nome.contains(param));
    }


}
