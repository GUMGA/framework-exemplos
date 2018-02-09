package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaSequeciaIdService;
import br.com.gumgaModel.domain.model.PessoaSequeciaId;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para implementação dos seeds de PessoaSequeciaId
 */
@Component
public class PessoaSequenciaIdSeed implements AppSeed {

    @Autowired
    PessoaSequeciaIdService pessoaSequeciaIdService;

    @Override
    public void loadSeed() throws IOException {
        if (pessoaSequeciaIdService.hasData()){
            return;
        }

        List<PessoaSequeciaId> pessoas = new ArrayList<>();
        PessoaSequeciaId pessoa1 = new PessoaSequeciaId("Felipe S.", 25, 1.72, 108.5);
        pessoas.add(pessoa1);
        PessoaSequeciaId pessoa2 = new PessoaSequeciaId("Mateus M.", 21, 1.52, 95.0);
        pessoas.add(pessoa2);
        PessoaSequeciaId pessoa3 = new PessoaSequeciaId("Caio M.", 25, 1.61, 98.6);
        pessoas.add(pessoa3);
        PessoaSequeciaId pessoa4 = new PessoaSequeciaId("Gabi P.", 18, 1.59, 52.3);
        pessoas.add(pessoa4);
        PessoaSequeciaId pessoa5 = new PessoaSequeciaId("William D.", 26, 1.82, 78.7);
        pessoas.add(pessoa5);
        PessoaSequeciaId pessoa6 = new PessoaSequeciaId("Lusca K.", 22, 1.75, 49.8);
        pessoas.add(pessoa6);
        PessoaSequeciaId pessoa7 = new PessoaSequeciaId("Luiz P.", 45, 1.79, 85.0);
        pessoas.add(pessoa7);
        for (PessoaSequeciaId p : pessoas) {
            pessoaSequeciaIdService.save(p);
        }
    }
}
