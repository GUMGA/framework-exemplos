package br.com.remocaoLogica.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.remocaoLogica.domain.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, Long> {

    /**
     * Método que busca as pessoas removidas logicamente do banco de dados
     * @return
     */
    @Query("from Pessoa p where p.gumgaActive is false")
    List<Pessoa> trash();

}