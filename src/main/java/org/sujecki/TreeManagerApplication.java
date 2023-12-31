package org.sujecki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TreeManagerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TreeManagerApplication.class, args);
    }
}