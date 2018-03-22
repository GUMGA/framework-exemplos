package br.com.registrosCompartilhados.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.registrosCompartilhados.domain.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends GumgaCrudRepository<Item, Long> {}