package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaSharedAleatIdService;
import br.com.gumgaModel.domain.model.PessoaSharedAleatId;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PessoaSharedAleatIdSeed implements AppSeed {
    @Autowired
    PessoaSharedAleatIdService pessoaSharedAleatIdService;
    
    @Override
    public void loadSeed() throws IOException {

        if (pessoaSharedAleatIdService.hasData()){
            return;
        }

        List<PessoaSharedAleatId> pessoas = new ArrayList<>();
        PessoaSharedAleatId pessoa1 = new PessoaSharedAleatId("Felipe S.", 25, 1.72, 108.5);
        pessoas.add(pessoa1);
        PessoaSharedAleatId pessoa2 = new PessoaSharedAleatId("Mateus M.", 21, 1.52, 95.0);
        pessoas.add(pessoa2);
        PessoaSharedAleatId pessoa3 = new PessoaSharedAleatId("Caio M.", 25, 1.61, 98.6);
        pessoas.add(pessoa3);
        PessoaSharedAleatId pessoa4 = new PessoaSharedAleatId("Gabi P.", 18, 1.59, 52.3);
        pessoas.add(pessoa4);
        PessoaSharedAleatId pessoa5 = new PessoaSharedAleatId("William D.", 26, 1.82, 78.7);
        pessoas.add(pessoa5);
        PessoaSharedAleatId pessoa6 = new PessoaSharedAleatId("Lusca K.", 22, 1.75, 49.8);
        pessoas.add(pessoa6);
        PessoaSharedAleatId pessoa7 = new PessoaSharedAleatId("Luiz P.", 45, 1.79, 85.0);
        pessoas.add(pessoa7);
        for (PessoaSharedAleatId p : pessoas) {
            pessoaSharedAleatIdService.save(p);
        }
    }
}
