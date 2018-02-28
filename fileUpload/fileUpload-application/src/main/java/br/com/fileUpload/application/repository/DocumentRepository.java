package br.com.fileUpload.application.repository;

import io.gumga.domain.repository.GumgaCrudRepository;
import br.com.fileUpload.domain.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends GumgaCrudRepository<Document, Long> {}