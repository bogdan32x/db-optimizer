package ro.utcluj.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "schemaDetails")
public class SchemaDetails {

	@Id
	private String	id;

	@Field(value = "sn")
	private String	schemaName;

	@Field(value = "tt")
	private long	totalTables;

	@Field(value = "pt")
	private long	parsedTables;

	@Field(value = "cd")
	private Date	creationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public long getTotalTables() {
		return totalTables;
	}

	public void setTotalTables(long totalTables) {
		this.totalTables = totalTables;
	}

	public long getParsedTables() {
		return parsedTables;
	}

	public void setParsedTables(long parsedTables) {
		this.parsedTables = parsedTables;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
