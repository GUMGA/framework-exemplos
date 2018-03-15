package br.com.gumgaNLP.application.service;

import io.gumga.application.GumgaService;

import io.gumga.application.nlp.GumgaNLP;
import io.gumga.domain.nlp.GumgaNLPThing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.gumgaNLP.application.repository.ProdutoRepository;
import br.com.gumgaNLP.domain.model.Produto;

import java.util.List;


@Service
@Transactional
public class ProdutoService extends GumgaService<Produto, String> {

    private final static Logger LOG = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoRepository repositoryProduto;

    @Autowired
    private GumgaNLP gnlp;

    @Autowired
    public ProdutoService(ProdutoRepository repository) {
        super(repository);
        this.repositoryProduto = repository;
    }

    public Produto nlpTextConverter(String texto, String verbos) {
        try {

            List<Object> p = gnlp.createObjectsFromDocument(texto, verbos);
            System.out.println(p);

            Produto produto = (Produto) p.get(0);
            save(produto);
            return produto;
        } catch (Exception e) {
            return null;
        }
    }

}