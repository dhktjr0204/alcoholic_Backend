package graduation.alcoholic;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties()
public class AlcoholicApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:application-oauth.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlcoholicApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
