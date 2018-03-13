package br.com.customFields.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.customFields.domain.model.Conta;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends GumgaCrudRepository<Conta, Long> {}