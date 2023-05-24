package us.teachmeskills.springdataJpa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("us.teachmeskills.springdataJpa.repositories")
public class Lesson45SpringDataJpaApplication {
    private final Environment environment;

    public Lesson45SpringDataJpaApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson45SpringDataJpaApplication.class, args);
    }
}
