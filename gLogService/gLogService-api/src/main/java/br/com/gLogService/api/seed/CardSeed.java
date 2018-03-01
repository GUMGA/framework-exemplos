package br.com.gLogService.api.seed;

import br.com.gLogService.application.service.CardService;
import br.com.gLogService.domain.model.Card;
import io.gumga.application.GumgaLoggerService;
import io.gumga.domain.seed.AppSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CardSeed implements AppSeed {

    @Autowired
    private CardService cardService;

    @Autowired
    private GumgaLoggerService gumgaLoggerService;

    @Override
    public void loadSeed() throws IOException {
        if (cardService.hasData()){
            gumgaLoggerService.logToFile("Dados encontrados na base",1);
            return;
        }


        cardService.save(new Card(" JULIAN GOODMAN",5262062479306930L,"06/18",669));
        cardService.save(new Card(" KATHERINE SHERLOCK",5490760595761037L,"12/19",880));
        cardService.save(new Card(" MICHAEL SMITH",5283144957083382L,"10/19",741));
        cardService.save(new Card(" LOGAN DUTTON",5225432194962067L,"08/19",464));
    }
}
