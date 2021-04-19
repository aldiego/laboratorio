package br.com.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("br.com.laboratory")
@SpringBootApplication
public class LaboratoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryApplication.class, args);
    }

}
