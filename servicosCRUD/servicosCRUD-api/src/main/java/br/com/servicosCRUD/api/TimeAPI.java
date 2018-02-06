package br.com.servicosCRUD.api;

import br.com.servicosCRUD.application.service.TimeService;
import br.com.servicosCRUD.domain.model.Time;
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

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/time")
@Transactional
public class TimeAPI extends GumgaAPI<Time, String> {


    @Autowired
    public TimeAPI(GumgaService<Time, String> service) {
        super(service);
    }

    @Override
    public Time load(@PathVariable String id) {
        return ((TimeService) service).loadTimeFat(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "yardToMeter", notes = "Conversor de Jardas para Metros")
    @RequestMapping(value = "/yardToMeter/{yard}", method = RequestMethod.GET)
    public String yardToMeter(@PathVariable Integer yard) {
        return yard + " jarda(s) equivalem a " + yard * 0.9144 + " metros!";
    }
}