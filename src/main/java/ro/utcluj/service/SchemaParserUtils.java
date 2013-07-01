package ro.utcluj.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ro.utcluj.dto.SchemaDetails;
import ro.utcluj.dto.Table;
import ro.utcluj.dto.TableProp;

public class SchemaParserUtils {

	protected static Logger		logger					= Logger.getLogger(SchemaParserUtils.class);

	// private static final String TABLE_DETECTION_REGEX = "(?<=TABLE\\s)[\\w`\\s]+[(][\\w\\s``(),]+[)]";
	private static final String	TABLE_DETECTION_REGEX	= "(?<=CREATE TABLE\\s)[\\w`\\s]+[\\w\\s`(),'.-:-\\[\\]]+[)]";

	// private static final String TABLE_DETECTION_REGEX =
	// "(?:CREATE|TABLE|IF|NOT|EXISTS)[\\w`\\s]+[\\w\\s`(),'.-:-\\[\\]]+[)]";

	/**
	 * This method is used to parse the Table Schema in SQL format and extract relevant information from it.
	 * 
	 * @param cachedString
	 * @param fileName
	 */
	public static List<Table> parseSchema(final StringBuilder cachedString, String fileName, SchemaDetails schema) {
		final List<String> tableGroupsList = new ArrayList<String>();
		final List<Table> tableDtoList = new ArrayList<Table>();
		final Pattern p = Pattern.compile(SchemaParserUtils.TABLE_DETECTION_REGEX, Pattern.MULTILINE);
		final Matcher m = p.matcher(cachedString);
		while (m.find()) {
			String matchedTable = m.group();
			matchedTable = matchedTable.replace("IF NOT EXISTS", "");
			tableGroupsList.add(matchedTable.trim());
			matchedTable = null;
		}

		schema.setCreationDate(new Date());
		schema.setTotalTables(tableGroupsList.size());
		schema.setSchemaName(fileName);
		long validTables = 0;
		for (String tableGroup : tableGroupsList) {

			// if (!BracketBalancingStringValidator.isBalanced(tableGroup)) {
			// logger.error("Cannot validate given SQL group: " + tableGroup.trim());
			// continue;
			// }

			validTables++;
			final Table tableDto = new Table();
			int tableNameRightIndex = tableGroup.indexOf("(", 1);

			String tableName = new String(tableGroup.substring(0, tableNameRightIndex));
			if (tableName.contains(".")) {
				tableName = new String(tableName.split("\\.")[1]);
			}
			tableName = tableName.replaceAll("`", "");
			tableDto.setTableName(tableName.trim());

			tableGroup = new String(tableGroup.substring(tableNameRightIndex + 1));
			tableGroup = tableGroup.replaceFirst("[(]", "");
			final int endChar = tableGroup.lastIndexOf(")");
			tableGroup = new String(tableGroup.substring(0, endChar - 1));

			Pattern splitPattern = Pattern.compile(",$", Pattern.MULTILINE);
			final List<String> tableProperties = Arrays.asList(splitPattern.split(tableGroup));

			final List<TableProp> tablePropList = new ArrayList<TableProp>();
			for (final String tableField : tableProperties) {
				final TableProp tableProp = new TableProp();
				String tempString = tableField.trim();

				if (tableField.contains("NOT NULL")) {
					tableProp.setNullable(false);
					tempString = new String(tempString.substring(0, tempString.indexOf("NOT NULL")));
				} else {
					if (tableField.contains("NULL")) {
						tableProp.setNullable(true);
						tempString = new String(tempString.substring(0, tempString.indexOf("NULL")));
					}
				}

				final List<String> tablePropFields = Arrays.asList(tempString.split("[\\s]+"));
				if (tablePropFields.size() >= 2) {
					if (!tablePropFields.get(0).equals("KEY") && !tablePropFields.get(0).equals("PRIMARY")) {
						tableProp.setPropName(filterString(tablePropFields.get(0)));
						tableProp.setPropType(tablePropFields.get(1));
						tablePropList.add(tableProp);
						logger.info(tableProp);
					}

				}
			}
			tableDto.setTableProperties(tablePropList);
			tableDto.setSchemaName(fileName);
			if (tablePropList.size() > 0) {
				tableDtoList.add(tableDto);
			}
		}
		schema.setParsedTables(validTables);
		System.gc();
		return tableDtoList;
	}

	/**
	 * Method used for filtering strings from any unwanted characters and trimming them from extra spaces.
	 * 
	 * @param unfilteredString
	 * @return
	 */
	private static String filterString(final String unfilteredString) {
		final String filteredString = unfilteredString.replaceAll("[^a-zA-Z0-9_]+", "").trim();
		return filteredString;
	}

}
