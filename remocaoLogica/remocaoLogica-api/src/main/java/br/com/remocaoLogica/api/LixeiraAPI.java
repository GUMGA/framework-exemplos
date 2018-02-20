package br.com.remocaoLogica.api;

import br.com.remocaoLogica.domain.model.Pessoa;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lixeira")
@Transactional
public class LixeiraAPI extends GumgaAPI<Pessoa, Long> {

    @Autowired
    public LixeiraAPI(GumgaService<Pessoa, Long> service) {
        super(service);
    }

    @RequestMapping("/id")
    public String teste(){
        return "vai malandra";
    }

}
