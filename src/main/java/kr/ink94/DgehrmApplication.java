package kr.ink94;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DgehrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgehrmApplication.class, args);
	}

}
