package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaSharedRemocaoLogicaService;
import br.com.gumgaModel.domain.model.PessoaSharedRemocaoLogica;
import br.com.gumgaModel.domain.model.PessoaSharedRemocaoLogica;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PessoaSharedLDSeed implements AppSeed {
    @Autowired
    private PessoaSharedRemocaoLogicaService pessoaSharedRemocaoLogicaService;

    @Override
    public void loadSeed() throws IOException {
        if (pessoaSharedRemocaoLogicaService.hasData()){
            return;
        }
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Felipe S.", 25, 1.72, 108.5));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Caio M.", 25, 1.61, 98.6));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Mateus M.", 21, 1.52, 95.0));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Gabi P.", 18, 1.59, 52.3));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("William D.", 26, 1.82, 78.7));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Lusca K.", 22, 1.75, 49.8));
        pessoaSharedRemocaoLogicaService.save(new PessoaSharedRemocaoLogica("Luiz P.", 45, 1.79, 85.0));
    }
}
