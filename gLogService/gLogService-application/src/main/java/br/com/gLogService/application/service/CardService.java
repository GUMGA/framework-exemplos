package br.com.gLogService.application.service;

import io.gumga.application.GumgaLogService;
import io.gumga.application.GumgaLoggerService;
import io.gumga.application.GumgaService;

import io.gumga.core.GumgaValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.com.gLogService.application.repository.CardRepository;
import br.com.gLogService.domain.model.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Transactional
public class CardService extends GumgaService<Card, String> {

    private static final Logger log = LoggerFactory.getLogger(GumgaLogService.class);


    @Autowired
    private GumgaValues gumgaValues;

    private final static Logger LOG = LoggerFactory.getLogger(CardService.class);
    private final CardRepository repositoryCard;

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Autowired
    public CardService(CardRepository repository) {
        super(repository);
        this.repositoryCard = repository;
    }

    public boolean hasData() {
        return repositoryCard.count() > 0;
    }

    public String ownLogWriter(String message) {
        logToFile_(message);
        return "Mensagem Própria gravada com sucesso!";
    }

    public String stdLogWriter(String message) {
        gumgaLoggerService.logToFile(message, 3);
        return "Mensagem Padrão gravada com sucesso!";
    }


    public void logToFile_(String msg) {
        try {
            String line = createLogLine_(msg);
            File folder = new File(gumgaValues.getLogDir());
            folder.mkdirs();
            File log = new File(folder, "ownLog.txt");
            FileWriter fw = new FileWriter(log, true);
            fw.write(line);
            fw.close();
        } catch (IOException ex) {
            log.error("Problema ao loggar no arquivo", ex);
        }
    }

    public String createLogLine_(String msg) {
        String line = "Esta é uma mensagem de log personalizada, o texto recebido é: " + msg + "\n";
        return line;
    }


}