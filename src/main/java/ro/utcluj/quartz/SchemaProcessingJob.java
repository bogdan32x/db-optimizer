package ro.utcluj.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import ro.utcluj.dto.SchemaDetails;
import ro.utcluj.dto.Table;
import ro.utcluj.mongo.repository.DbOptimizerRepository;
import ro.utcluj.service.SchemaParserUtils;

@Component
@ComponentScan(basePackages = "ro.utcluj.mongo.repository")
public class SchemaProcessingJob {

	@Autowired
	DbOptimizerRepository	dbOptimizerRepository;

	Logger					log	= Logger.getLogger(SchemaProcessingJob.class);

	@PostConstruct
	public void execute() {
		new Thread(new InnerJob()).start();
	}

	private StringBuilder readFile(File file) {
		BufferedReader reader;
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			log.error(e.getClass() + ": " + e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getClass() + ": " + e.getMessage(), e);
		}

		return stringBuilder;
	}

	public DbOptimizerRepository getMongoRep() {
		return dbOptimizerRepository;
	}

	public void setMongoRep(DbOptimizerRepository tableMongoRepository) {
		this.dbOptimizerRepository = tableMongoRepository;
	}

	class InnerJob implements Runnable {

		public void run() {
			while (true) {
				try {
					log.debug("Waiting for 60 seconds...");
					Thread.sleep(60000);
					log.debug("Starting processing...");

					File rawFolder = new File("d:/rawSql");
					final File processedFolder = new File("d:/processedSql");

					if (rawFolder.exists()) {
						for (final File rawSqlFile : rawFolder.listFiles()) {
							if (rawSqlFile.exists() && rawSqlFile.isFile() && rawSqlFile.canRead()) {
								new Thread(new Runnable() {

									public void run() {
										log.error("Reading file..." + rawSqlFile.getName());
										SchemaDetails schema = new SchemaDetails();
										List<Table> processedTables = SchemaParserUtils.parseSchema(readFile(rawSqlFile), rawSqlFile.getName(), schema);
										dbOptimizerRepository.saveAllTables(processedTables);
										dbOptimizerRepository.saveSchemaDetails(schema);
										log.error("Processed file..." + rawSqlFile.getName() + " tables found:"
												+ processedTables.size());

										String newFilePath = rawSqlFile.getName();
										File newFile = new File(processedFolder, newFilePath);

										try {
											FileUtils.moveFile(rawSqlFile, newFile);
										} catch (IOException e) {
											log.error(e.getMessage(), e);
										}
										System.gc();
									}
								}).start();
							}
						}

					}

				} catch (Exception e) {
					log.error(e.getClass() + ": " + e.getMessage(), e);
				}
			}
		}
	}
}
