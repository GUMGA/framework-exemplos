package br.com.seeds.api.seed;

import br.com.seeds.application.service.CarrinhoService;
import br.com.seeds.application.service.ProdutoService;
import br.com.seeds.domain.model.Carrinho;
import br.com.seeds.domain.model.ItemCarrinho;
import br.com.seeds.domain.model.Produto;
import io.gumga.application.GumgaLoggerService;
import io.gumga.core.QueryObject;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CarrinhoSeed implements AppSeed {

    private static final Random random = new Random();
    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Override
    public void loadSeed() throws IOException {
        if (carrinhoService.hasData()) {
            gumgaLoggerService.logToFile("Dados encontrados no repositório", 1);
            System.out.println("++++++++++++Dados encontrados no repositório++++++++++++");
            return;

        }
        Carrinho carrinho1 = new Carrinho(9.90, "Caio");
        Carrinho carrinho2 = new Carrinho(10.90, "Mateus");
        Carrinho carrinho3 = new Carrinho(6.90, "Felipe");
        List<Carrinho> carrinhos = new ArrayList<>();
        carrinhos.add(carrinho1);
        carrinhos.add(carrinho2);
        carrinhos.add(carrinho3);


        List<Produto> produtos = produtoService.pesquisa(new QueryObject()).getValues();
        for (Carrinho c : carrinhos) {
            int index = random.nextInt(produtos.size());
            System.out.println("------------------------------------  indice  " + index);
            for (int i = 0; i < index + 1; i++) {
                System.out.println(c.getNomeUsuario());
                c.getItens().add(new ItemCarrinho(produtos.get(random.nextInt(produtos.size()))));
            }
            carrinhoService.save(c);
        }
    }
}
