package br.com.templatesFreemaker.application.service;

import io.gumga.application.GumgaService;

import io.gumga.application.service.GumgaFreemarkerTemplateEngineService;
import io.gumga.core.GumgaThreadScope;
import io.gumga.core.GumgaValues;
import io.gumga.core.exception.TemplateEngineException;
import io.gumga.core.service.GumgaAbstractTemplateEngineAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.templatesFreemaker.application.repository.PessoaRepository;
import br.com.templatesFreemaker.domain.model.Pessoa;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, String> implements EmailService<Pessoa> {

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repositoryPessoa;

    private List<String> emails = new ArrayList<>();

    @Autowired
    private GumgaAbstractTemplateEngineAdapter templateEngineService;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
        this.repositoryPessoa = repository;
    }


    public String sendMail(Pessoa pessoa) {

        Map<String, Object> map = new HashMap<>();

        //Criando o conjunto de dados a serem associados ao template
        map.put("nome", pessoa.getNome());
        map.put("email", pessoa.getEmail());
        map.put("url", "https://gumga.github.io/#/app/home");

        try {
            String htmlTemplate = templateEngineService.parse(map, "emailTemplate.ftl");
            System.out.println(htmlTemplate);
            emails.add(htmlTemplate);
            return htmlTemplate;
        } catch (TemplateEngineException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Pessoa save(Pessoa resource) {
        sendMail(resource);
        return super.save(resource);
    }

    @Override
    public List<String> getEmails() {
        return emails;
    }
}