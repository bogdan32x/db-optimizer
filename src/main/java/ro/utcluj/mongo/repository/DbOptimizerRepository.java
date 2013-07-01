package ro.utcluj.mongo.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import ro.utcluj.dto.SchemaDetails;
import ro.utcluj.dto.Table;

@Repository
@Component
public class DbOptimizerRepository {

	@Autowired
	MongoTemplate	mongoTemplate;

	Logger			logger	= Logger.getLogger(DbOptimizerRepository.class);

	public DbOptimizerRepository() {}

	public void saveAllTables(List<Table> processedTables) {
		for (Table t : processedTables) {
			// logger.info(t);
			mongoTemplate.save(t);
		}
	}

	public void saveSchemaDetails(SchemaDetails schemaDetails) {
		if (schemaDetails != null) {
			mongoTemplate.save(schemaDetails);
		}
	}

	public List<Table> getAllTables() {

		Query q = new Query();
		q.addCriteria(Criteria.where("tp").not().size(0));
		List<Table> tl = mongoTemplate.find(q, Table.class);
		if (tl.size() <= 0) {
			logger.error("Ceeeeeeeee?");
		}
		return tl;

	}
}
