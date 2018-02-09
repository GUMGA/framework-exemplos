package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaSharedSeqIdService;
import br.com.gumgaModel.domain.model.PessoaSharedSeqId;
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
public class PessoaSharedSeqIdSeed implements AppSeed {
    
    @Autowired
    PessoaSharedSeqIdService pessoaSharedSeqIdService;

    @Override
    public void loadSeed() throws IOException {
        if (pessoaSharedSeqIdService.hasData()){
            return;
        }

        List<PessoaSharedSeqId> pessoas = new ArrayList<>();
        PessoaSharedSeqId pessoa1 = new PessoaSharedSeqId("Felipe S.", 25, 1.72, 108.5);
        pessoas.add(pessoa1);
        PessoaSharedSeqId pessoa2 = new PessoaSharedSeqId("Mateus M.", 21, 1.52, 95.0);
        pessoas.add(pessoa2);
        PessoaSharedSeqId pessoa3 = new PessoaSharedSeqId("Caio M.", 25, 1.61, 98.6);
        pessoas.add(pessoa3);
        PessoaSharedSeqId pessoa4 = new PessoaSharedSeqId("Gabi P.", 18, 1.59, 52.3);
        pessoas.add(pessoa4);
        PessoaSharedSeqId pessoa5 = new PessoaSharedSeqId("William D.", 26, 1.82, 78.7);
        pessoas.add(pessoa5);
        PessoaSharedSeqId pessoa6 = new PessoaSharedSeqId("Lusca K.", 22, 1.75, 49.8);
        pessoas.add(pessoa6);
        PessoaSharedSeqId pessoa7 = new PessoaSharedSeqId("Luiz P.", 45, 1.79, 85.0);
        pessoas.add(pessoa7);
        for (PessoaSharedSeqId p : pessoas) {
            pessoaSharedSeqIdService.save(p);
        }
    }
}
