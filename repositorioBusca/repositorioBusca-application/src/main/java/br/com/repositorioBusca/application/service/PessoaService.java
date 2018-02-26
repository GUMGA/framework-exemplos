package br.com.repositorioBusca.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.com.repositorioBusca.application.repository.PessoaRepositorySpring;
import br.com.repositorioBusca.domain.model.Pessoa;

import java.util.List;


@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepositorySpring repositoryPessoa;

    @Autowired
    public PessoaService(PessoaRepositorySpring repository) {
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

    public List<Pessoa> getPessoasIMCMenorQue(Double param){
        return repositoryPessoa.findByImcLessThan(param);
    }

    public List<Pessoa> getPessoasIMCIdadeMenorQue(Double imc, int idade){
        return repositoryPessoa.findByImcLessThanEqualAndIdadeLessThanEqual(imc, idade);
    }
}