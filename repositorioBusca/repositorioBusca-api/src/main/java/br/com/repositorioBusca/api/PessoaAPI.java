package br.com.repositorioBusca.api;

import br.com.repositorioBusca.application.service.PessoaService;
import br.com.repositorioBusca.domain.model.Pessoa;
import io.gumga.application.GumgaService;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import io.gumga.presentation.RestResponse;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import io.gumga.application.GumgaTempFileService;
import io.gumga.domain.domains.GumgaImage;
import io.gumga.presentation.GumgaAPI;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pessoa")
@Transactional
public class PessoaAPI extends GumgaAPI<Pessoa, Long> {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    public PessoaAPI(GumgaService<Pessoa, Long> service) {
        super(service);
    }

    @RequestMapping("/imc/{param}")
    public List<Pessoa> getImc(@PathVariable("param") Double param) {
        return pessoaService.getPessoasIMCMenorQue(param);
    }


    @RequestMapping("/fat")
    public List<Pessoa> getImc(@RequestParam(value = "imc") Double imc, @RequestParam(value = "idade") int idade) {
        return pessoaService.getPessoasIMCIdadeMenorQue(imc, idade);
    }


}