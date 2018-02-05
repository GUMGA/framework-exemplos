package br.com.servicosCRUD.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.servicosCRUD.domain.model.Time;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends GumgaCrudRepository<Time, String> {}