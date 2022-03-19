package graduation.alcoholic;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties()
public class AlcoholicApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";


	public static void main(String[] args) {
		new SpringApplicationBuilder(AlcoholicApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}
}

