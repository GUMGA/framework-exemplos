package br.com.seeds.api.seed;

import br.com.seeds.application.service.ProdutoService;
import br.com.seeds.domain.model.Produto;
import io.gumga.application.GumgaLoggerService;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProdutoSeed implements AppSeed {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    GumgaLoggerService gumgaLoggerService;

    @Override
    public void loadSeed() throws IOException {
        if (produtoService.hasData()) {
            gumgaLoggerService.logToFile("Dados encontrados no repositório", 1);
            return;
        }
        Produto produto1 = new Produto("Cartão de Memória", 89.90, 0.09);
        produtoService.save(produto1);
        Produto produto2 = new Produto("Placa Mãe", 569.90, 2.12);
        produtoService.save(produto2);
        Produto produto3 = new Produto("Fone Headset", 128.50, 1.79);
        produtoService.save(produto3);
        Produto produto4 = new Produto("Monitor Gamer", 799.90, 5.90);
        produtoService.save(produto4);
        Produto produto5 = new Produto("Processador", 1289.90, 1.25);
        produtoService.save(produto5);
        Produto produto6 = new Produto("Antivírus", 69.90, 0.0);
        produtoService.save(produto6);
        Produto produto7 = new Produto("Mouse sem Fio", 199.90, 0.6);
        produtoService.save(produto7);

    }
}
