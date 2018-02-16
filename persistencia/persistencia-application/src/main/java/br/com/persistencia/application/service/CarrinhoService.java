package br.com.persistencia.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.persistencia.application.repository.CarrinhoRepository;
import br.com.persistencia.domain.model.Carrinho;


@Service
@Transactional
public class CarrinhoService extends GumgaService<Carrinho, String> {

    private final static Logger LOG = LoggerFactory.getLogger(CarrinhoService.class);
    private final CarrinhoRepository repositoryCarrinho;

    @Autowired
    public CarrinhoService(CarrinhoRepository repository) {
        super(repository);
        this.repositoryCarrinho = repository;
    }

}