package br.com.gumgaQuery.api.seed;
import br.com.gumgaQuery.application.service.PessoaService;
import br.com.gumgaQuery.domain.model.Pessoa;
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
        if (pessoaService.hasData()) {
            return;
        }
        GumgaThreadScope.organizationCode.set("1.");
        pessoaService.save(new Pessoa("Caito", 24, 89.0, 1.75));
        pessoaService.save(new Pessoa("Luiz", 45, 71.0, 1.75));
        pessoaService.save(new Pessoa("Mateus", 17, 90.0, 1.55));
        pessoaService.save(new Pessoa("Felipe", 25, 102.0, 1.80));
        pessoaService.save(new Pessoa("Gabriela", 18, 46.0, 1.65));
        pessoaService.save(new Pessoa("Lucas", 22, 68.0, 1.79));
        pessoaService.save(new Pessoa("Bruno", 30, 82.0, 1.68));
        pessoaService.save(new Pessoa("Xung Li", 56, 93.0, 1.33));
        pessoaService.save(new Pessoa("Ligia", 47, 68.0, 1.92));
        pessoaService.save(new Pessoa("Siro", 39, 99.0, 1.82));
        pessoaService.save(new Pessoa("Brian", 31, 140.0, 1.85));
        pessoaService.save(new Pessoa("Alisson", 32, 160.0, 1.97));
        pessoaService.save(new Pessoa("Helen", 29, 58.0, 1.48));
        pessoaService.save(new Pessoa("Fernanda", 15, 57.0, 1.62));
        pessoaService.save(new Pessoa("Thiago", 31, 80.0, 1.81));
        pessoaService.save(new Pessoa("Diego", 26, 98.0, 1.95));
        pessoaService.save(new Pessoa("Carolina", 23, 82.0, 1.71));
        pessoaService.save(new Pessoa("Lorraine", 81, 57.3, 1.67 ));
        pessoaService.save(new Pessoa("Larissa", 37, 77.2, 1.63));
        pessoaService.save(new Pessoa("Sophia", 30, 85.4, 1.57));
        pessoaService.save(new Pessoa("Erick", 25, 92.6, 1.68));
        pessoaService.save(new Pessoa("Antônio", 27, 90.5, 1.70));
        pessoaService.save(new Pessoa("Kauã", 30, 71.1, 1.75));
        pessoaService.save(new Pessoa("Beatrice", 45, 90.8, 1.54));
    }
}