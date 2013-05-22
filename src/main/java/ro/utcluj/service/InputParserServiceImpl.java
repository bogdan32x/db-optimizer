package ro.utcluj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ro.utcluj.dto.TableDTO;
import ro.utcluj.dto.TablePropDTO;

public class InputParserServiceImpl {

	protected static Logger		logger					= Logger.getLogger(InputParserServiceImpl.class);

	private static final String	TABLE_DETECTION_REGEX	= "(?<=TABLE\\s)[\\w`\\s]+[(][\\w\\s``(),]+[)]";

	public void parseSchema(final String initialSchema) {
		final List<String> tableGroupsList = new ArrayList<String>();
		final List<TableDTO> tableDtoList = new ArrayList<TableDTO>();

		final Pattern p = Pattern.compile(InputParserServiceImpl.TABLE_DETECTION_REGEX, Pattern.MULTILINE);
		final Matcher m = p.matcher(initialSchema);
		while (m.find()) {
			tableGroupsList.add(m.group());
		}

		for (String tableGroup : tableGroupsList) {
			final TableDTO tableDto = new TableDTO();
			final int tableNameRightIndex = tableGroup.indexOf("`", 1);

			final String tableName = tableGroup.substring(1, tableNameRightIndex);
			tableDto.setTableName(tableName);

			tableGroup = tableGroup.substring(tableNameRightIndex + 1).trim();
			tableGroup = tableGroup.replaceFirst("[(]", "");
			final int endChar = tableGroup.lastIndexOf(")");
			tableGroup = tableGroup.substring(0, endChar - 1).trim();

			final String[] tableProperties = tableGroup.split(",");

			final List<TablePropDTO> tablePropList = new ArrayList<TablePropDTO>();
			for (final String tableField : tableProperties) {
				final TablePropDTO tableProp = new TablePropDTO();
				String tempString = tableField;

				if (tableField.contains("NOT NULL")) {
					tableProp.setNullable(false);
					tempString = tempString.substring(0, tempString.indexOf("NOT NULL")).trim();
					InputParserServiceImpl.logger.info(tempString);
				} else {
					if (tableField.contains("NULL")) {
						tableProp.setNullable(true);
						tempString = tempString.substring(0, tempString.indexOf("NULL")).trim();
						InputParserServiceImpl.logger.info(tempString);
					}
				}

				final String[] tablePropFields = tempString.split("[\\s]+");
				if (tablePropFields.length == 2) {
					tableProp.setPropName(this.filterString(tablePropFields[0]));
					tableProp.setPropType(tablePropFields[1]);
					tablePropList.add(tableProp);
				}
				// InputParserServiceImpl.logger.info(tableField + "\n");
			}
			tableDto.setTableProperties(tablePropList);

			// InputParserServiceImpl.logger.info(tableDto.toString());
		}
		// return null;
	}

	private String filterString(final String unfilteredString) {
		final String filteredString = unfilteredString.replaceAll("[^a-zA-Z0-9_]+", "").trim();
		InputParserServiceImpl.logger.info(filteredString);
		return filteredString;
	}
}
