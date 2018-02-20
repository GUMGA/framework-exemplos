package br.com.gumgaModel.api.seed;

import br.com.gumgaModel.application.service.PessoaRemocaoLogicaService;
import br.com.gumgaModel.domain.model.PessoaRemocaoLogica;
import br.com.gumgaModel.domain.model.PessoaRemocaoLogica;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PessoaLDSeed implements AppSeed {
    
    @Autowired
    private PessoaRemocaoLogicaService pessoaRemocaoLogicaService;
    
    @Override
    public void loadSeed() throws IOException {
        if (pessoaRemocaoLogicaService.hasData()){
            return;
        }
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Felipe S.", 25, 1.72, 108.5));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Caio M.", 25, 1.61, 98.6));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Mateus M.", 21, 1.52, 95.0));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Gabi P.", 18, 1.59, 52.3));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("William D.", 26, 1.82, 78.7));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Lusca K.", 22, 1.75, 49.8));
        pessoaRemocaoLogicaService.save(new PessoaRemocaoLogica("Luiz P.", 45, 1.79, 85.0));

        
        
    }
}
