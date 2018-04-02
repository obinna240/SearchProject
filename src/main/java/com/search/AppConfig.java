package com.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import com.search.docs.DocumentIndexer;
import com.search.docs.NoRefOne;

import com.search.utils.NoRefTwo;

//@Import(LoggerConfig.class)
//HAS TO BE IN THE SAME PACKAGE AS SEARCHPROJECTAPPLICATION
@Configuration //configuration class specifying the beans to be created
@ComponentScan(basePackageClasses={NoRefOne.class, NoRefTwo.class})
@PropertySource(value = {"classpath:application.properties" })
public class AppConfig
{
	@Autowired
	Environment env; //application properties is loaded into Spring's Enviroment class
	
	@Bean
	public  DocumentIndexer documentIndexer()
	{
		
		return new DocumentIndexer(env.getProperty("solr.index.local.url"), env.getProperty("solr.index.local.name"));
	}
	
	/**
	 * This configures a propertyPlaceholder configurer that we can use
	 * for component scanned beans like DirectoryWalker,
	 * Check out the property "_index_directory"
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() 
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}




/**

@Configuration
@ComponentScan(basePackageClasses={NoRefOne.class, NoRefTwo.class, NoRefThree.class})
@PropertySource(value="classpath:application.properties",ignoreResourceNotFound=true)
public class AustraliaIndexerConfig
{
	@Autowired
	private Environment env;
	
	
	@Bean
	public Initializer initializer()
	{
		String csvLocation = env.getProperty("csv.location");
		
		String smtpHost = env.getProperty("smtp.host");
		String smtpPort = env.getProperty("smtp.port");
		String smtpEmailFrom = env.getProperty("smtp.email.from"); 
		
		String smtpEmailTo = env.getProperty("smtp.email.to"); 
		String smtpEmailSubject = env.getProperty("smtp.email.subject"); 
		
		String solrCore= env.getProperty("solr.core"); 
		String solrHost= env.getProperty("solr.host"); 
		String solrPort= env.getProperty("solr.port"); 
		
		String recordNumber = env.getProperty("recordNumber");
		
		return new Initializer(csvLocation, smtpHost, smtpPort, smtpEmailFrom, 
				smtpEmailTo, smtpEmailSubject, solrCore, solrHost, solrPort, recordNumber);
	}
	
	
	
}
*/
