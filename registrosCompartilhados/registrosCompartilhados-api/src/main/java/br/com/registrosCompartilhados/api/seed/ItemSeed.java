package br.com.registrosCompartilhados.api.seed;

import br.com.registrosCompartilhados.application.service.ItemService;
import br.com.registrosCompartilhados.domain.model.Categorias;
import br.com.registrosCompartilhados.domain.model.Item;
import io.gumga.core.GumgaThreadScope;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ItemSeed implements AppSeed {
    @Autowired
    private ItemService itemService;

    @Override
    public void loadSeed() throws IOException {
        if (itemService.hasData()) {
            return;
        }
        GumgaThreadScope.organizationCode.set("1.");
        Item item1 = new Item("Coca-Cola", "Refrigerante Lata", new BigDecimal(4), Categorias.BEBIDA);
        item1.addOrganization("2.");
        item1.addOrganization("3.");
        itemService.save(item1);
        Item item2 = new Item("Água sem Gás", "Garrafa 600ml", new BigDecimal(4), Categorias.BEBIDA);
        item2.addOrganization("2.");
        item2.addOrganization("3.");
        itemService.save(item2);
        Item item3 = new Item("Batata Frita com Queijo e Bacon", "Porção de Batata Frita Com Queijo e Bacon", new BigDecimal(18), Categorias.COMIDA);
        item3.addOrganization("2.");
        item3.addOrganization("3.");
        itemService.save(item3);
        Item item4 = new Item("Polenta Frita", "Porção de Polenta Frita", new BigDecimal(15), Categorias.COMIDA);
        item4.addOrganization("2.");
        item4.addOrganization("3.");
        itemService.save(item4);
    }
}
