package br.com.gLogService.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.gLogService.domain.model.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends GumgaCrudRepository<Card, String> {}