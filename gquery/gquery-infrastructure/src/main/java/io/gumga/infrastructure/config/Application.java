package io.gumga.infrastructure.config;

import io.gumga.gquery.configuration.web.WebConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan(basePackages = {
        "io.gumga.gquery",
        "io.gumga"
})
@EnableWebMvc
@Import(WebConfiguration.class)
public class Application {

}
