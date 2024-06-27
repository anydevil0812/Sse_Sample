package co.kr.pando.pando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PandoApplication {

	public static void main(String[] args) {

		SpringApplication.run(PandoApplication.class, args);
	}

}
