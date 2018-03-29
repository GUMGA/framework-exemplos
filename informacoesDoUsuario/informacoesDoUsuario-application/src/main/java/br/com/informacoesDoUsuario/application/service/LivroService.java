package br.com.informacoesDoUsuario.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.informacoesDoUsuario.application.repository.LivroRepository;
import br.com.informacoesDoUsuario.domain.model.Livro;


@Service
@Transactional
public class LivroService extends GumgaService<Livro, String> {

    private final static Logger LOG = LoggerFactory.getLogger(LivroService.class);
    private final LivroRepository repositoryLivro;

    @Autowired
    public LivroService(LivroRepository repository) {
        super(repository);
        this.repositoryLivro = repository;
    }



}