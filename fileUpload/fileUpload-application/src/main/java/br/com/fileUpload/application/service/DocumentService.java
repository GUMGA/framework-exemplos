package br.com.fileUpload.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.fileUpload.application.repository.DocumentRepository;
import br.com.fileUpload.domain.model.Document;


@Service
@Transactional
public class DocumentService extends GumgaService<Document, Long> {

    private final static Logger LOG = LoggerFactory.getLogger(DocumentService.class);
    private final DocumentRepository repositoryDocument;

    @Autowired
    public DocumentService(DocumentRepository repository) {
        super(repository);
        this.repositoryDocument = repository;
    }

}