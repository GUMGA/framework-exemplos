package br.com.gumgaModel.api;

import br.com.gumgaModel.application.service.PessoaAleatorioIdService;
import br.com.gumgaModel.domain.model.PessoaAleatorioId;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pessoaaleatorioid")
@Transactional
public class PessoaAleatorioIdAPI extends GumgaAPI<PessoaAleatorioId, String> {


    @Autowired
    public PessoaAleatorioIdAPI(GumgaService<PessoaAleatorioId, String> service) {
        super(service);
    }


}