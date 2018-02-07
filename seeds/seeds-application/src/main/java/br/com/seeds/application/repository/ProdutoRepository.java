package br.com.seeds.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.seeds.domain.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends GumgaCrudRepository<Produto, String> {}