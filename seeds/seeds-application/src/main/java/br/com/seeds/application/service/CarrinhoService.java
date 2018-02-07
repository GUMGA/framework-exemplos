package br.com.seeds.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.seeds.application.repository.CarrinhoRepository;
import br.com.seeds.domain.model.Carrinho;

import br.com.seeds.domain.model.Produto;

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

    @Transactional
    public Carrinho loadCarrinhoFat(String id) {
        Carrinho obj = view(id);
        /**
         * Este método carrega o carrinho com todas as suas associações,
         * ou seja, busca todos os itens associados ao carrinho referenciado
         */
            Hibernate.initialize(obj.getItens());


        return obj;
    }

    /**
     * Verifica se já existem instâncias dessa entidade salva no banco de dados
     * @return
     */
    public boolean hasData(){
        return repositoryCarrinho.count() > 0;
    }
}