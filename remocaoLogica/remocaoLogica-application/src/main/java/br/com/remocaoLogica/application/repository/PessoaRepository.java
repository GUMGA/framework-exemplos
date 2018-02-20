package br.com.remocaoLogica.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.remocaoLogica.domain.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends GumgaCrudRepository<Pessoa, Long> {
//TODO delete comments
//    List<Pessoa> findByPesoAndAltura(Double peso, Double altura);
//
//    void deleteNomeContaining(String nome);
//
//    @Query(value = "from Pessoa as p where p.peso = :pesoDoCara and p.altura = :altura", nativeQuery = false)
//    List<Pessoa> kdsgfvdksad(@Param("pesoDoCara") Double peso, @Param("altura") Double altura);

//    void deleteById(Long id);

}