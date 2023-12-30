package com.github.sebastianp265.notekeeper.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NoteKeeper backend REST API")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sebastian Pawliński")
                                .email("pawlinskisebastian@gmail.com")
                                .url("https://github.com/sebastianp265"))
                );
    }
}
