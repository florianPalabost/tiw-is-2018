package fr.univlyon1.tiw.tiw1.tp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"fr.univlyon1.tiw"})
@EntityScan(basePackages = {"fr.univlyon1.tiw"})
@ComponentScan(basePackages = { "fr.univlyon1.tiw" })
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
