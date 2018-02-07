package br.com.seeds.api.seed;

import io.gumga.application.GumgaLoggerService;
import io.gumga.domain.seed.AppSeed;
import br.com.seeds.configuration.security.RegisterApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Esta classe implementa os métodos que são chamados na inicialização do sistema,
 * fazendo a chamada de implementação dos seeds
 */
@Component
class Seed implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Autowired
    private RegisterApplication registerApplication;



    private AtomicBoolean started = new AtomicBoolean(false);


    @Autowired
    private ProdutoSeed produtoSeed;

    @Autowired
    private CarrinhoSeed carrinhoSeed;

    /**
     * Chama uma lista com os objetos que contém os seeds a serem persistidos
     * @param event
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (started.get()) {
            return;
        }
        started.set(true);
        registerApplication.register();
        gumgaLoggerService.logToFile("Start ", 1);
        for (AppSeed seed : seeds()) {
            try {
                seed.loadSeed();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        gumgaLoggerService.logToFile("End ", 2);

    }

    private List<AppSeed> seeds() {
        List<AppSeed> list = new LinkedList<>();
        list.add(produtoSeed);
        list.add(carrinhoSeed);
        // Basta adicionar a list objetos correspondentes as classes de seeds criadas
        return list;
    }

}

