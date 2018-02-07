package br.com.seeds.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.seeds.application.repository.ItemCarrinhoRepository;
import br.com.seeds.domain.model.ItemCarrinho;


@Service
@Transactional
public class ItemCarrinhoService extends GumgaService<ItemCarrinho, String> {

    private final static Logger LOG = LoggerFactory.getLogger(ItemCarrinhoService.class);
    private final ItemCarrinhoRepository repositoryItemCarrinho;

    @Autowired
    public ItemCarrinhoService(ItemCarrinhoRepository repository) {
        super(repository);
        this.repositoryItemCarrinho = repository;
    }

}