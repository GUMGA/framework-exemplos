package br.com.variavelAmbiente.api;

import br.com.variavelAmbiente.application.service.PessoaService;
import br.com.variavelAmbiente.domain.model.Pessoa;
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
import java.util.Map;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pessoa")
@Transactional
public class PessoaAPI extends GumgaAPI<Pessoa, String> {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    public PessoaAPI(GumgaService<Pessoa, String> service) {
        super(service);
    }

    @RequestMapping("/filepropertieslocal")
    @Transactional
    public Properties getLocalFileProperties() {
        return pessoaService.getCustomLocalFileProperties();
    }
    @RequestMapping("/fileproperties")
    @Transactional
    public Properties getFileProperties() {
        return pessoaService.getRootsFileProp();
    }

    @RequestMapping("/system")
    @Transactional
    public Map<String, String> getSystemProperties() {
        return pessoaService.systemProperties();
    }

    @RequestMapping("/getProp")
    @Transactional
    public Properties getProp() {
        return pessoaService.getProperties();
    }
    @RequestMapping("/enviroment")
    @Transactional
    public List<String> enviroment() {
        return pessoaService.getEnviroment();
    }

}