package site.rubi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class RubiApplication {

    public static void main(String[] args) {
        System.out.println(
                "Diretório do banco: " + System.getProperty("user.dir"));
        SpringApplication.run(RubiApplication.class, args);
    }
}
