package br.com.repositorioBusca.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.repositorioBusca.domain.model.Produto;
import io.gumga.domain.repository.GumgaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends GumgaQueryDSLRepository<Produto, Long> {


}