package br.com.fileUpload.api;

import br.com.fileUpload.application.service.DocumentService;
import br.com.fileUpload.domain.model.Document;
import com.wordnik.swagger.annotations.ApiOperation;
import io.gumga.annotations.GumgaSwagger;
import io.gumga.application.GumgaService;
import io.gumga.domain.domains.GumgaFile;
import io.gumga.presentation.GumgaAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import io.gumga.presentation.RestResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import io.gumga.application.GumgaTempFileService;
import io.gumga.domain.domains.GumgaImage;
import io.gumga.presentation.GumgaAPI;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
@Transactional
public class DocumentAPI extends GumgaAPI<Document, Long> {

    @Autowired
    private GumgaTempFileService gumgaTempFileService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    public DocumentAPI(GumgaService<Document, Long> service) {
        super(service);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/file")
    public String FileUpload(@RequestParam MultipartFile file) throws IOException {
        System.out.println("UPLOAD Arquivo");
        GumgaFile gf = new GumgaFile();
        gf.setBytes(file.getBytes());
        gf.setMimeType(file.getContentType());
        gf.setName(file.getName());
        gf.setSize(file.getSize());
        String fileName = gumgaTempFileService.create(gf);

        Document document = new Document();
        document.setFile(gf);
        documentService.save(document);
        document.getFile().setName(fileName);
        return document.getFile().getName();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/file")
    public String FileDelete(String fileName) {
        return gumgaTempFileService.delete(fileName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file/{fileName}")
    public byte[] FileGet(@PathVariable(value = "fileName") String fileName) {
        return gumgaTempFileService.find(fileName).getBytes();
    }

    @Override
    @GumgaSwagger
    @Transactional
    @ApiOperation(value = "save", notes = "Salva o objeto correspodente.")
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse<Document> save(@RequestBody @Valid Document obj, BindingResult result) {
        if (obj.getFile() != null) {
            obj.setFile((GumgaImage) gumgaTempFileService.find(obj.getFile().getName()));
        }
        return super.save(obj, result);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public RestResponse<Document> update(Long id, @RequestBody @Valid Document obj, BindingResult result) {
        if (obj.getFile() != null) {
            if ("null".equals(obj.getFile().getName())) {
                obj.setFile(null);
            } else if (obj.getFile().getSize() == 0) {
                obj.setFile((GumgaImage) gumgaTempFileService.find(obj.getFile().getName()));
            }
        }
        return super.update(id, obj, result);
    }

    @RequestMapping(value = "/get/{id}")
    public void getFile(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        GumgaFile file = documentService.view(id).getFile();
        httpServletResponse.reset();
        httpServletResponse.setContentLengthLong(file.getSize());
        httpServletResponse.setContentType(file.getMimeType());
        InputStream fis = new ByteArrayInputStream(file.getBytes());

        ServletOutputStream fos = httpServletResponse.getOutputStream();
        while (fis.available() > 0) {
            byte buffer[] = new byte[fis.available() > 4096 ? 4096 : fis.available()];
            fis.read(buffer);
            fos.write(buffer);
        }
        fos.close();
        fis.close();
    }
}