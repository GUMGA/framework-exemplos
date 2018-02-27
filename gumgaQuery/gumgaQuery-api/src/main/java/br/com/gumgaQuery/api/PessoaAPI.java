package br.com.gumgaQuery.api;

import br.com.gumgaQuery.application.service.PessoaService;
import br.com.gumgaQuery.domain.model.Pessoa;
import io.gumga.application.GumgaService;
import io.gumga.core.gquery.ComparisonOperator;
import io.gumga.core.gquery.Criteria;
import io.gumga.core.gquery.GQuery;
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
    private PessoaService pessoaService;

    @Autowired
    public PessoaAPI(GumgaService<Pessoa, String> service) {
        super(service);
    }

    @RequestMapping("/getg/{param}")
    public List<Pessoa> getListPessoaGquery(@PathVariable("param") String param){
        return pessoaService.getListPessoaGquery(param);
    }
    @RequestMapping("/geto/{param}")
    public List<Pessoa> getListPessoaQo(@PathVariable("param") String param){
        return pessoaService.getListPessoaQo(param);
    }
    @RequestMapping("/gq")
    public List<Pessoa> getListPessoaQo(@RequestBody GQuery gq){
        return pessoaService.getListPessoaGqObj(gq);
//        return pessoaService.getListPessoaQo(param);
    }




}