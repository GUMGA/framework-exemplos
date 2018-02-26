package io.gumga.gquery.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.QueryObject;
import io.gumga.core.gquery.ComparisonOperator;
import io.gumga.core.gquery.Criteria;
import io.gumga.core.gquery.GQuery;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import io.gumga.gquery.application.repository.PessoaRepository;
import io.gumga.gquery.domain.model.Pessoa;

import io.gumga.gquery.domain.model.Endereco;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, String> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repositoryPessoa;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
        this.repositoryPessoa = repository;
    }

    @Transactional
    public Pessoa loadPessoaFat(String id) {
        Pessoa obj = view(id);

            Hibernate.initialize(obj.getEnderecos());


        return obj;
    }


    public List<Pessoa> exemploContains(String param) {
        GQuery gQuery = new GQuery(new Criteria("obj.nome", ComparisonOperator.CONTAINS, param));

        QueryObject queryObject = new QueryObject();
        queryObject.setgQuery(gQuery);

        return pesquisa(queryObject).getValues();
    }


    public List<Pessoa> exemploEqual(String param) {
        GQuery gQuery = new GQuery(new Criteria("obj.nome", ComparisonOperator.EQUAL, param));

        QueryObject queryObject = new QueryObject();
        queryObject.setgQuery(gQuery);

        return pesquisa(queryObject).getValues();

    }

    public List<Pessoa> exemploBetween(int param1, int param2) {

        GQuery gQuery = new GQuery(new Criteria("obj.idade", ComparisonOperator.BETWEEN, Arrays.asList(param1, param2)));

        QueryObject queryObject = new QueryObject();
        queryObject.setgQuery(gQuery);

        return pesquisa(queryObject).getValues();

    }

    public  List<Pessoa> exemploLike(String param){
        GQuery gQuery = new GQuery(new Criteria("obj.nome", ComparisonOperator.LIKE, param));

        QueryObject queryObject = new QueryObject();
        queryObject.setgQuery(gQuery);

        return pesquisa(queryObject).getValues();

    }

    public  List<Pessoa> exemploGreater(int param){
        GQuery gQuery = new GQuery(new Criteria("obj.idade", ComparisonOperator.GREATER, param));

        QueryObject queryObject = new QueryObject();
        queryObject.setgQuery(gQuery);

        return pesquisa(queryObject).getValues();

    }
}