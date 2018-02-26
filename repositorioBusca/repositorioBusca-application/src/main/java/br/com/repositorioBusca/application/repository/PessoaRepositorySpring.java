package br.com.repositorioBusca.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.repositorioBusca.domain.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepositorySpring extends GumgaCrudRepository<Pessoa, Long> {

    List<Pessoa> findByImcLessThan(Double imc);

    List<Pessoa> findByImcLessThanEqualAndIdadeLessThanEqual(Double imc, int idade);

}