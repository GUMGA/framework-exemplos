package br.com.repositorioBusca.api.seed;

import br.com.repositorioBusca.application.service.ProdutoService;
import br.com.repositorioBusca.domain.model.Produto;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProdutoSeed implements AppSeed {
    @Autowired
    private ProdutoService produtoService;

    @Override
    public void loadSeed() throws IOException {
        if(produtoService.hasData()){
            return;
        }
        produtoService.save(new Produto("Câmera Filmadora", 110.0, 45,0.7,17.9 ));
        produtoService.save(new Produto("Bote Barco Inflável", 478.0, 26,12.7,4.9 ));
        produtoService.save(new Produto("Kit Churrasco", 45.0, 68,1.7,17.9 ));
        produtoService.save(new Produto("Patins Quad", 799.0, 19,2.5,8.4 ));
        produtoService.save(new Produto("Bola De Futebol", 69.0, 37,0.7,17.9 ));
        produtoService.save(new Produto("Mini Refrigerador", 209.0, 24,14.7,7.2 ));
        produtoService.save(new Produto("Balde De Gelo", 333.0, 18,0.8,14.2 ));
        produtoService.save(new Produto("Caixa de Som", 800.0, 18,1.7,10.2 ));
        produtoService.save(new Produto("Micro-ondas", 449.0, 21,12.7,9.2 ));
        produtoService.save(new Produto("Processador de Alimentos", 243.0, 55,4.7,9.7 ));

    }
}
