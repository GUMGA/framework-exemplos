package br.com.seeds.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.seeds.domain.model.Carrinho;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends GumgaCrudRepository<Carrinho, String> {}