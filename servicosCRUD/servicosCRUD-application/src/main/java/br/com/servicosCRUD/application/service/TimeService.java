package br.com.servicosCRUD.application.service;

import io.gumga.application.GumgaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

import br.com.servicosCRUD.application.repository.TimeRepository;
import br.com.servicosCRUD.domain.model.Time;

import br.com.servicosCRUD.domain.model.Pessoa;

@Service
@Transactional
public class TimeService extends GumgaService<Time, String> {

    private final static Logger LOG = LoggerFactory.getLogger(TimeService.class);
    private final TimeRepository repositoryTime;

    @Autowired
    public TimeService(TimeRepository repository) {
        super(repository);
        this.repositoryTime = repository;
    }

    @Transactional
    public Time loadTimeFat(String id) {
        Time obj = view(id);

            Hibernate.initialize(obj.getJogadores());


        return obj;
    }
}