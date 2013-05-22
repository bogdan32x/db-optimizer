package ro.utcluj.dto;

import java.util.List;

public class TableDTO {

	private String				tableName;

	private List<TablePropDTO>	tableProperties;

	public String getTableName() {
		return this.tableName;
	}

	public List<TablePropDTO> getTableProperties() {
		return this.tableProperties;
	}

	public void setTableProperties(final List<TablePropDTO> tableProperties) {
		this.tableProperties = tableProperties;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(this.tableName + " ");
		for (final TablePropDTO tab : this.tableProperties) {
			sb.append(tab.toString());
		}

		return sb.toString();
	}
}
