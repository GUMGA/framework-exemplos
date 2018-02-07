package br.com.seeds.api.seed;

import br.com.seeds.application.service.ProdutoService;
import br.com.seeds.domain.model.Produto;
import io.gumga.application.GumgaLoggerService;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Classe que implementa a criação do seed de produto
 */
@Component
public class ProdutoSeed implements AppSeed {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    GumgaLoggerService gumgaLoggerService;

    /**
     * Método para a instanciação dos objetos a serem persistidos na inicialização do servidor;
     * Você pode implementar qualquer tipo de lógica para a obtenção desses dados, inclusive
     * fazer chamada a uma API externa para geração de informações;
     *
     * Neste exemplo criamos algumas instancias de maneira manual, utilizando o construtor que criamos
     * na classe Produto {@link Produto}
     * @throws IOException
     */
    @Override
    public void loadSeed() throws IOException {

        if (produtoService.hasData()) {
            /**Este if verifica se já existem objetos desta entidade persistidos*/
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
        /**
         * A chamada do método produtoService.save utiliza do próprio serviço CRUD implementado
         * para manipular os seeds no banco de dados
         * {@link ProdutoService}
         */

    }
}
