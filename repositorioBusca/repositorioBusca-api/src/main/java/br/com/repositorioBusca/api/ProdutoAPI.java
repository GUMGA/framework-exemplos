package br.com.repositorioBusca.api;

import br.com.repositorioBusca.application.service.ProdutoService;
import br.com.repositorioBusca.domain.model.Produto;
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
@RequestMapping("/api/produto")
@Transactional
public class ProdutoAPI extends GumgaAPI<Produto, Long> {


    @Autowired
    public ProdutoAPI(GumgaService<Produto, Long> service) {
        super(service);
    }

    @Autowired
    private ProdutoService service;

    @RequestMapping("/precoVendaGT/{param}")
    public List<Produto> getGTProdutoPrecoVenda(@PathVariable("param") Double param){
        return service.getListGTProdutoPrecoVenda(param);
    }

    @RequestMapping("/margemLOE/{param}")
    public List<Produto> getLOEProdutoPrecoVenda(@PathVariable("param") Double param){
        return service.getListLOEProdutoMargem(param);
    }



}