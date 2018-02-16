package br.com.persistencia.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.persistencia.domain.model.Carrinho;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends GumgaCrudRepository<Carrinho, String> {}