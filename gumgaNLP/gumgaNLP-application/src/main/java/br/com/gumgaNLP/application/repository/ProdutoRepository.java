package br.com.gumgaNLP.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gumgaNLP.domain.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends GumgaCrudRepository<Produto, String> {}