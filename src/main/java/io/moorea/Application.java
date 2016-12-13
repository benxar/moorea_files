package io.moorea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	public Application(){
		// SpringApplication.run(Application.class);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(applicationClass);
    }

    public static void main(String[] args) throws Exception {
       SpringApplication.run(applicationClass, args);
    }
    
    private static Class<Application> applicationClass = Application.class;

}