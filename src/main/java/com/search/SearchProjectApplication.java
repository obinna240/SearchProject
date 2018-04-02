package com.search;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.search.utils.DirectoryWalker;


@SpringBootApplication(exclude = { SolrAutoConfiguration.class })
public class SearchProjectApplication //implements ApplicationRunner
{
	private static final Logger logger = LogManager.getLogger(SearchProjectApplication.class);
	
	
	public static void main(String[] args)
	{
		logger.info("Starting Spring Application");
		SpringApplication.run(SearchProjectApplication.class, args);
	}
	
	/** Un comment this to run as a command line argument
	 @Bean
	 public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	        return args -> {

	          //  System.out.println("Let's inspect the beans provided by Spring Boot:");

	          //  String[] beanNames = ctx.getBeanDefinitionNames();
	          //  Arrays.sort(beanNames);
	          //  for (String beanName : beanNames) {
	          //      System.out.println(beanName);
	          //  }
	        	
	            DirectoryWalker dw = (DirectoryWalker) ctx.getBean(DirectoryWalker.class);
	            dw.run();
	        };
	    }

	*/
	 
	/**
	 * 
	 *
	@Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.debug("Debugging log");
        logger.info("Info log");
        logger.warn("Hey, This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Damn! Fatal error. Please fix me.");
    }*/
}
