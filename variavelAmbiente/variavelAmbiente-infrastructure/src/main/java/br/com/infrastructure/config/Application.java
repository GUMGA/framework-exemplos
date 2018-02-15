package br.com.infrastructure.config;

import br.com.variavelAmbiente.configuration.web.WebConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan(basePackages = {
        "br.com.variavelAmbiente",
        "io.gumga"
})
@EnableWebMvc
@Import(WebConfiguration.class)
public class Application {

}
