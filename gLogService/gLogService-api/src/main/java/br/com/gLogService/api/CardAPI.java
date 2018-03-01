package br.com.gLogService.api;

import br.com.gLogService.api.seed.CardSeed;
import br.com.gLogService.application.service.CardService;
import br.com.gLogService.domain.model.Card;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import io.gumga.presentation.RestResponse;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import io.gumga.application.GumgaTempFileService;
import io.gumga.domain.domains.GumgaImage;
import io.gumga.presentation.GumgaAPI;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/card")
@Transactional
public class CardAPI extends GumgaAPI<Card, String> {


    @Autowired
    public CardAPI(GumgaService<Card, String> service) {
        super(service);
    }

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/logA", method = RequestMethod.GET)
    public String fileLoggerStd(@RequestHeader String message) {
        return cardService.stdLogWriter(message);
    }

    @RequestMapping(value = "/logB", method = RequestMethod.GET)
    public String fileLoggerOwn(@RequestHeader String message) {
        return cardService.ownLogWriter(message);
    }

}