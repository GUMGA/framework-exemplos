package br.com.persistencia.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.persistencia.domain.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends GumgaCrudRepository<Produto, Long> {}