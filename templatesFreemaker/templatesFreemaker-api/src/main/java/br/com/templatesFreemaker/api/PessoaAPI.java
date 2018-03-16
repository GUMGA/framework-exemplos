package br.com.templatesFreemaker.api;

import br.com.templatesFreemaker.application.service.EmailService;
import br.com.templatesFreemaker.application.service.PessoaService;
import br.com.templatesFreemaker.domain.model.Pessoa;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import io.gumga.presentation.RestResponse;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
@Transactional
public class PessoaAPI extends GumgaAPI<Pessoa, String> {


    private final EmailService<Pessoa> emailService;



    @Autowired
    public PessoaAPI(@Qualifier("pessoaService") PessoaService service, EmailService<Pessoa> emailService) {
        super(service);
        this.emailService = emailService;
    }


    @RequestMapping(value = "/emails", method = RequestMethod.GET)
    public List getEmails() {
        return emailService.getEmails();
    }
}