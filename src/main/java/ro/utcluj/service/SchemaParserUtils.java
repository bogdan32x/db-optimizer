package ro.utcluj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ro.utcluj.dto.Table;
import ro.utcluj.dto.TableProp;
import ro.utcluj.utils.validators.BracketBalancingStringValidator;

public class SchemaParserUtils {

	protected static Logger		logger					= Logger.getLogger(SchemaParserUtils.class);

	// private static final String TABLE_DETECTION_REGEX = "(?<=TABLE\\s)[\\w`\\s]+[(][\\w\\s``(),]+[)]";
	private static final String	TABLE_DETECTION_REGEX	= "(?<=TABLE\\s)[\\w`\\s]+[\\w\\s`(),'.-:-\\[\\]]+[)]";

	/**
	 * This method is used to parse the Table Schema in SQL format and extract relevant information from it.
	 * 
	 * @param string
	 * @param fileName
	 */
	public static List<Table> parseSchema(final String string, String fileName) {
		final List<String> tableGroupsList = new ArrayList<String>();
		final List<Table> tableDtoList = new ArrayList<Table>();

		final Pattern p = Pattern.compile(SchemaParserUtils.TABLE_DETECTION_REGEX, Pattern.MULTILINE);
		final Matcher m = p.matcher(string);
		while (m.find()) {
			tableGroupsList.add(m.group());
		}

		for (String tableGroup : tableGroupsList) {

			if (!BracketBalancingStringValidator.isBalanced(tableGroup, "")) {
				logger.error("Cannot validate given SQL group: " + tableGroup.trim());
				continue;
			}

			final Table tableDto = new Table();
			int tableNameRightIndex = tableGroup.indexOf("`", 1);
			if (tableNameRightIndex == -1) {
				tableNameRightIndex = 1;
			}

			final String tableName = tableGroup.substring(1, tableNameRightIndex);
			tableDto.setTableName(tableName);

			tableGroup = tableGroup.substring(tableNameRightIndex + 1).trim();
			tableGroup = tableGroup.replaceFirst("[(]", "");
			final int endChar = tableGroup.lastIndexOf(")");
			tableGroup = tableGroup.substring(0, endChar - 1).trim();

			final String[] tableProperties = tableGroup.split(",");

			final List<TableProp> tablePropList = new ArrayList<TableProp>();
			for (final String tableField : tableProperties) {
				final TableProp tableProp = new TableProp();
				String tempString = tableField;

				if (tableField.contains("NOT NULL")) {
					tableProp.setNullable(false);
					tempString = tempString.substring(0, tempString.indexOf("NOT NULL")).trim();
					// InputParserServiceImpl.logger.info(tempString);
				} else {
					if (tableField.contains("NULL")) {
						tableProp.setNullable(true);
						tempString = tempString.substring(0, tempString.indexOf("NULL")).trim();
						// InputParserServiceImpl.logger.info(tempString);
					}
				}

				final String[] tablePropFields = tempString.split("[\\s]+");
				if (tablePropFields.length == 2) {
					tableProp.setPropName(filterString(tablePropFields[0]));
					tableProp.setPropType(tablePropFields[1]);
					tablePropList.add(tableProp);
					logger.info(tableProp);
				}
				// InputParserServiceImpl.logger.info(tableField + "\n");
			}
			tableDto.setTableProperties(tablePropList);
			tableDto.setTableName(fileName);
			if (tablePropList.size() > 0) {
				tableDtoList.add(tableDto);
			}
			// InputParserServiceImpl.logger.info(tableDto.toString());
		}
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
		// InputParserServiceImpl.logger.info(filteredString);
		return filteredString;
	}

}
