package br.com.variavelAmbiente.application.service;

import io.gumga.application.GumgaService;

import io.gumga.core.GumgaValues;
import org.omg.CORBA.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.variavelAmbiente.application.repository.PessoaRepository;
import br.com.variavelAmbiente.domain.model.Pessoa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Service
@Transactional
public class PessoaService extends GumgaService<Pessoa, String> {

    @Autowired
    org.springframework.core.env.Environment ambiente;

    @Autowired
    private GumgaValues gumgaValues;

    private final static Logger LOG = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repositoryPessoa;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        super(repository);
        this.repositoryPessoa = repository;
    }

    public Map<String, String> systemProperties() {
        return System.getenv();
    }

    public Properties getProperties() {
        return System.getProperties();
    }

    public Properties getCustomLocalFileProperties() {
        Properties toReturn = new Properties();
        try {
            InputStream input = new FileInputStream(System.getProperty("user.dir") + "/" + getCustomPropertiesFileName());
            toReturn.load(input);
        } catch (IOException e) {
        }
        return toReturn;
    }

    public Properties getRootsFileProp() {
        Properties toReturn = gumgaValues.getCustomFileProperties();
        return toReturn;
    }


    private String getCustomPropertiesFileName() {
        return "arquivoPropriedades.properties";
    }

    public List<String> getEnviroment() {
        List<String> vars = new ArrayList<>();
        vars.add(ambiente.getProperty("variavel.var1"));
        vars.add(ambiente.getProperty("variavel.var2"));
        vars.add(ambiente.getProperty("variavel.var3"));
        vars.add(ambiente.getProperty("variavel.var4"));
        return vars;
    }

}