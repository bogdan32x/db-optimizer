package ro.utcluj.mongo.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories
public class OptimizerMongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "dbOpt";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1");
	}

	@Override
	protected String getMappingBasePackage() {
		return "ro.utcluj.mongo";
	}

}
