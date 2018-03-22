package br.com.registrosCompartilhados.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.gquery.GQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.registrosCompartilhados.application.repository.ItemRepository;
import br.com.registrosCompartilhados.domain.model.Item;

import java.util.List;


@Service
@Transactional
public class ItemService extends GumgaService<Item, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository repositoryItem;

    @Autowired
    public ItemService(ItemRepository repository) {
        super(repository);
        this.repositoryItem = repository;
    }

    public boolean hasData(){
        return repositoryItem.count() > 0;
    }

    public List<Item> BuscaItemBebidas(GQuery gQuery) {
        return repositoryItem.findAll(gQuery);
    }

    public List<Item> BuscaItemComidas(GQuery gQuery) {
        return repositoryItem.findAll(gQuery);
    }
}