package graduation.alcoholic;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServletInitializer {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml";
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AlcoholicApplication.class)
                .properties(APPLICATION_LOCATIONS);
    }
}
