package br.com.gumgaModel.boot;

import io.gumga.application.GumgaManagerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {
        "io.gumga",
        "br.com.gumgaModel"
})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}

