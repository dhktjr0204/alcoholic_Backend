package graduation.alcoholic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class AlcoholicApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(AlcoholicApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}
}

