package io.gumga.buscatextual.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import io.gumga.buscatextual.domain.model.Empresa;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends GumgaCrudRepository<Empresa, String> {}