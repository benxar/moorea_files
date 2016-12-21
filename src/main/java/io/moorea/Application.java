package io.moorea;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import io.moorea.configuration.ConfigurationReader;

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
       ConfigurationReader cr = new ConfigurationReader();
       if(cr.getConfiguration())
    	   SpringApplication.run(applicationClass, args);
       else
    	   throw new IOException("Config file couldn't be read");
    }
    
    private static Class<Application> applicationClass = Application.class;

}