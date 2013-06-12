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

import ro.utcluj.dto.Table;
import ro.utcluj.mongo.repository.TableMongoRepository;
import ro.utcluj.service.SchemaParserUtils;

@Component
@ComponentScan(basePackages = "ro.utcluj.mongo.repository")
public class SchemaProcessingJob {

	@Autowired
	TableMongoRepository	tableMongoRepository;

	Logger					log	= Logger.getLogger(SchemaProcessingJob.class);

	@PostConstruct
	public void execute() {
		new Thread(new InnerJob()).start();
	}

	@SuppressWarnings("resource")
	private String readFile(File file) {
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

		return stringBuilder.toString();
	}

	public TableMongoRepository getMongoRep() {
		return tableMongoRepository;
	}

	public void setMongoRep(TableMongoRepository tableMongoRepository) {
		this.tableMongoRepository = tableMongoRepository;
	}

	class InnerJob implements Runnable {

		public void run() {
			while (true) {
				try {
					log.debug("Waiting for 60 seconds...");
					Thread.sleep(60000);
					log.debug("Starting processing...");

					File rawFolder = new File("d:/rawSql");
					File processedFolder = new File("d:/processedSql");

					if (rawFolder.exists()) {
						for (File rawSqlFile : rawFolder.listFiles()) {
							if (rawSqlFile.exists() && rawSqlFile.isFile() && rawSqlFile.canRead()) {
								log.error("Reading file..." + rawSqlFile.getName());
								List<Table> processedTables = SchemaParserUtils.parseSchema(readFile(rawSqlFile),
										rawSqlFile.getName());
								tableMongoRepository.saveAll(processedTables);
								log.error("Processed file..." + rawSqlFile.getName() + " tables found:"
										+ processedTables.size());

								String newFilePath = rawSqlFile.getName();
								File newFile = new File(processedFolder, newFilePath);

								try {
									FileUtils.moveFile(rawSqlFile, newFile);
								} catch (IOException e) {
									log.error(e.getMessage(), e);
								}
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
