package br.com.gumgaQuery.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.GumgaThreadScope;
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

import br.com.gumgaQuery.application.repository.PessoaRepository;
import br.com.gumgaQuery.domain.model.Pessoa;

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

    public boolean hasData() {
        return repositoryPessoa.count() > 0;
    }

    @Override
    public Pessoa save(Pessoa resource) {
        resource.setImc((resource.getPeso() / (Math.pow(resource.getAltura(), 2))));
        return super.save(resource);
    }

    public List<Pessoa> getListPessoaGquery(String param) {
        /**
         * Este é o objeto principal da pesquisa, podemos criar todos os parâmetros de busca dentro do GQuery
         * e passá-lo como parâmetro para um método específico do repository
         */


        GQuery gQuery = new GQuery(new Criteria("obj.nome", ComparisonOperator.CONTAINS, param).addIgnoreCase().addTranslate());
        /**
         * Chamada do método que execura a busca no repository
         */
        return repositoryPessoa.findAll(gQuery);
    }

    public List<Pessoa> getListPessoaQo(String param) {
        /**
         * O objeto QueryObject contém campos de representação de uma pesquisa
         */
        QueryObject object = new QueryObject();
        /**
         * O campo Aq recebe diretamente uma hql
         */
        object.setAq("obj.nome like '%" + param + "%'");
        object.setPageSize(25);
        /**
         * O objeto de pesquisa pode ser passado por parâmetro a qualquer recurso de busca implementado
         */
        return repositoryPessoa.search(object).getValues();
    }

    public List<Pessoa> getListPessoaGqObj(GQuery gQuery){
        GumgaThreadScope.organizationCode.set(null);
        return repositoryPessoa.findAll(gQuery);
    }

}