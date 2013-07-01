package ro.utcluj.mining;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import ro.utcluj.dto.Table;
import ro.utcluj.mongo.repository.DbOptimizerRepository;

@Component
@ComponentScan(basePackages = "ro.utcluj.mongo.repository")
public class DataMiner {

	private ColumnClassifier		cc		= new ColumnClassifier();

	private TableClassifier			tc		= new TableClassifier();

	private InfrequentClassifier	ic		= new InfrequentClassifier();

	@Autowired
	DbOptimizerRepository			dbOptimizerRepository;

	Logger							logger	= Logger.getLogger(DataMiner.class);

	public void mineData() {

		List<Table> tableList = dbOptimizerRepository.getAllTables();
		for (Table t : tableList) {
			logger.error(t);
		}
	}

	public DbOptimizerRepository getMongoRep() {
		return dbOptimizerRepository;
	}

	public void setMongoRep(DbOptimizerRepository tableMongoRepository) {
		this.dbOptimizerRepository = tableMongoRepository;
	}

}
