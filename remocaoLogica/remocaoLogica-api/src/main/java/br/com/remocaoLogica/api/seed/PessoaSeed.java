package br.com.remocaoLogica.api.seed;

import br.com.remocaoLogica.application.service.PessoaService;
import br.com.remocaoLogica.domain.model.Pessoa;
import io.gumga.core.GumgaThreadScope;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class PessoaSeed implements AppSeed {
    @Autowired
    private PessoaService pessoaService;
    @Override
    public void loadSeed() throws IOException {
        if (pessoaService.hasData()){
            return;
        }
        GumgaThreadScope.organizationCode.set("1.");
        pessoaService.save(new Pessoa("Caito", 95.2,1.75));
        pessoaService.save(new Pessoa("Mateus", 98.4,1.58));
        pessoaService.save(new Pessoa("Felipe", 126.9,1.72));
    }
}
