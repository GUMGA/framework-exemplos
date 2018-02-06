package br.com.servicosCRUD.api;

import br.com.servicosCRUD.application.service.PessoaService;
import br.com.servicosCRUD.domain.model.Pessoa;
import com.wordnik.swagger.annotations.ApiOperation;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pessoa")
@Transactional
public class PessoaAPI extends GumgaAPI<Pessoa, String> {


    @Autowired
    public PessoaAPI(GumgaService<Pessoa, String> service) {
        super(service);
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "testeReq", notes = "Exemplo de método mapeado.")
    @RequestMapping(value = "/testeReq", method = RequestMethod.GET)
    public String testeReq() {
        return "Sua requisição foi recebida com sucesso!";
    }
}