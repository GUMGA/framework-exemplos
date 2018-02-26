package io.gumga.gquery.api;

import io.gumga.gquery.application.service.PessoaService;
import io.gumga.gquery.domain.model.Pessoa;
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
public class PessoaAPI extends GumgaAPI<Pessoa, String> {


    @Autowired
    public PessoaAPI(GumgaService<Pessoa, String> service) {
        super(service);
    }

    @Autowired
    public PessoaService pessoaService;

    @Override
    public Pessoa load(@PathVariable String id) {
        return ((PessoaService)service).loadPessoaFat(id);
    }

    @RequestMapping(value = "/gquery/contains", method = RequestMethod.GET)
    public List<Pessoa> exemploContainsAPI(@RequestParam(value = "nome", defaultValue = "") String param){
        return pessoaService.exemploContains(param);
    }

    @RequestMapping(value = "/gquery/equal", method = RequestMethod.GET)
    public List<Pessoa> exemploEqualAPI(@RequestParam(value ="nome", defaultValue = "Caio") String param){
        return pessoaService.exemploEqual(param);
    }

    @RequestMapping(value = "/gquery/between", method = RequestMethod.GET)
    public List<Pessoa> exemploBetweenAPI(@RequestParam(value= "idade", defaultValue = "") int param1, @RequestParam(value = "idade2", defaultValue = "") int param2){
        return pessoaService.exemploBetween(param1, param2);
     }

     @RequestMapping(value = "/gquery/like", method = RequestMethod.GET)
    public List<Pessoa> exemploLikeAPI(@RequestParam(value = "nome", defaultValue = "")String param){
        return pessoaService.exemploLike(param);
     }

    @RequestMapping(value = "/gquery/greater", method = RequestMethod.GET)
    public List<Pessoa> exemploGreaterAPI(@RequestParam(value = "idade", defaultValue = "")int param){
        return pessoaService.exemploGreater(param);
    }




}