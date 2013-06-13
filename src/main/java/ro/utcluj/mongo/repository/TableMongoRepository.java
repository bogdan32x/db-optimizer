package ro.utcluj.mongo.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import ro.utcluj.dto.Table;

@Repository
@Component
public class TableMongoRepository {

	@Autowired
	MongoTemplate	mongoTemplate;

	Logger			logger	= Logger.getLogger(TableMongoRepository.class);

	public TableMongoRepository() {}

	public void saveAll(List<Table> processedTables) {
		for (Table t : processedTables) {
			// logger.info(t);
			mongoTemplate.save(t);
		}
	}
}
