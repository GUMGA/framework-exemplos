package br.com.remocaoLogica.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.remocaoLogica.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, Long> {


}