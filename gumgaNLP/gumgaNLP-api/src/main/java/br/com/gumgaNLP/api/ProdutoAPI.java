package br.com.gumgaNLP.api;

import br.com.gumgaNLP.application.service.ProdutoService;
import br.com.gumgaNLP.domain.model.Produto;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/produto")
@Transactional
public class ProdutoAPI extends GumgaAPI<Produto, String> {

    @Autowired
    private ProdutoService service;

    @Autowired
    public ProdutoAPI(@Qualifier("produtoService") GumgaService<Produto, String> service) {
        super(service);
    }

    @RequestMapping(value = "/texto", method = RequestMethod.POST)
    public ResponseEntity nlpText(@RequestHeader String texto, @RequestHeader String verbos) {
        Produto produto = service.nlpTextConverter(texto, verbos);
        return produto != null ? ResponseEntity.status(201).body(produto)
                : ResponseEntity.status(204).body("Incapaz de instanciar um objeto");
    }
}