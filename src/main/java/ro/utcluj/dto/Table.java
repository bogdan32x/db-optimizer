package ro.utcluj.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "table")
public class Table {

	@Id
	private String			id;

	@Field(value = "tn")
	private String			tableName;

	@Field(value = "tp")
	private List<TableProp>	tableProperties;

	@Field(value = "sn")
	private String			schemaName;

	@Field(value = "cd")
	private Date			creationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTableName() {
		return this.tableName;
	}

	public List<TableProp> getTableProperties() {
		return this.tableProperties;
	}

	public void setTableProperties(final List<TableProp> tableProperties) {
		this.tableProperties = tableProperties;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

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

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(this.tableName + " ");
		for (final TableProp tab : this.tableProperties) {
			sb.append(tab.toString());
		}

		return sb.toString();
	}

}
