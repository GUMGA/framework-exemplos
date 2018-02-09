package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaAleatorioIdService;
import br.com.gumgaModel.domain.model.PessoaAleatorioId;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para implementação dos seeds de PessoaAleatorioIdService
 */
@Component
public class PessoaAleatorioIdSeed implements AppSeed {

    @Autowired
    PessoaAleatorioIdService pessoaAleatorioIdService;

    @Override
    public void loadSeed() throws IOException {
     if (pessoaAleatorioIdService.hasData()){
         return;
     }
        List<PessoaAleatorioId> pessoas = new ArrayList<>();
        PessoaAleatorioId pessoa1 = new PessoaAleatorioId("Felipe S.", 25, 1.72, 108.5);
        pessoas.add(pessoa1);
        PessoaAleatorioId pessoa2 = new PessoaAleatorioId("Mateus M.", 21, 1.52, 95.0);
        pessoas.add(pessoa2);
        PessoaAleatorioId pessoa3 = new PessoaAleatorioId("Caio M.", 25, 1.61, 98.6);
        pessoas.add(pessoa3);
        PessoaAleatorioId pessoa4 = new PessoaAleatorioId("Gabi P.", 18, 1.59, 52.3);
        pessoas.add(pessoa4);
        PessoaAleatorioId pessoa5 = new PessoaAleatorioId("William D.", 26, 1.82, 78.7);
        pessoas.add(pessoa5);
        PessoaAleatorioId pessoa6 = new PessoaAleatorioId("Lusca K.", 22, 1.75, 49.8);
        pessoas.add(pessoa6);
        PessoaAleatorioId pessoa7 = new PessoaAleatorioId("Luiz P.", 45, 1.79, 85.0);
        pessoas.add(pessoa7);
        for (PessoaAleatorioId p : pessoas) {
            pessoaAleatorioIdService.save(p);
        }

    }
}
