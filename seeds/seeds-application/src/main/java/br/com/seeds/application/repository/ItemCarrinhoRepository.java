package br.com.seeds.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.seeds.domain.model.ItemCarrinho;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends GumgaCrudRepository<ItemCarrinho, String> {}