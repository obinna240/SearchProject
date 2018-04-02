package com.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


/**
 * 
 * @author oonyimadu
 * By extending AbstractMongoConfiguration, we implicitly create a MongoTemplate, return the databasename
 * in getDataBaseBame() and a mongo client is also created
 * Normally sporing boot can create the mongo client and template bean for us using autoconfiguration
 * So we won't need MongoConfig.
 * However, we will use this if we need to configure our own mongo server and replicaset
 * For now we will ignore this
 */
@Configuration 
@PropertySource(value = {"classpath:application.properties" })
@EnableMongoRepositories("com.search.repository")
public class MongoConfig extends AbstractMongoConfiguration
{
	@Autowired
	Environment env;
	
	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return env.getProperty("spring.data.mongodb.database"); //return the name of the database
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(); //also include, the host and port i.e (localhost, 27017), env.getProperty("mongodb")
	}
	
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
	    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

}
