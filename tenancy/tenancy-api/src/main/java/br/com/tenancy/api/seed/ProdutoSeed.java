package br.com.tenancy.api.seed;

import br.com.tenancy.application.service.ProdutoService;
import br.com.tenancy.domain.model.Produto;
import io.gumga.core.GumgaThreadScope;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ProdutoSeed implements AppSeed {
    @Autowired
    ProdutoService produtoService;
    @Override
    public void loadSeed() throws IOException {
        if (produtoService.hasData()){
            return;
        }
        GumgaThreadScope.organizationCode.set("1.1.");
        produtoService.save(new Produto("Notebook Samsung",1200.0,25,4.6));
        produtoService.save(new Produto("Impressora Hp Deskjet",125.0,12,3.7));
        produtoService.save(new Produto("MacBook Air",3896.0,14,1.6));
        produtoService.save(new Produto("IMac Retina 5K",12850.0,17,7.6));


        GumgaThreadScope.organizationCode.set("2.1.");
        produtoService.save(new Produto("Multifuncional Epson",752.0,42,8.6));
        produtoService.save(new Produto("Hd Sata 1tb",189.0,61,0.8));
        produtoService.save(new Produto("Tablet Samsung",610.0,19,1.2));

        GumgaThreadScope.organizationCode.set("1.2.");
        produtoService.save(new Produto("Refrigerador Brastemp",980.0,28,80.6));
        produtoService.save(new Produto("Micro-ondas Brastemp",458.0,21,21.4));
        produtoService.save(new Produto("Fogão Top Gourmet",1200.0,49,16.4));

        GumgaThreadScope.organizationCode.set("2.2.");
        produtoService.save(new Produto("Fogão Havana",250.0,34,45.6));
        produtoService.save(new Produto("Frigobar Consul",458.0,27,46.4));
        produtoService.save(new Produto("Lavadora de Roupas Electrolux",893.0,29,90.4));

        GumgaThreadScope.organizationCode.set("1.3.");
        produtoService.save(new Produto("Guarda Roupa",890.0,39,110.6));
        produtoService.save(new Produto("Cadeira De Escritório",298.0,56,16.0));
        produtoService.save(new Produto("Kit 6 Poltronas",1080.0,29,120.4));

        GumgaThreadScope.organizationCode.set("2.3.");
        produtoService.save(new Produto("Sofá 4 Lugares",1090.0,39,98.6));
        produtoService.save(new Produto("Sofá Cama Casal",1204.0,19,163.0));
        produtoService.save(new Produto("Painel para TV",120.0,75,39.4));
        produtoService.save(new Produto("Mesa de Jantar",750.0,12,46.4));

    }
}
