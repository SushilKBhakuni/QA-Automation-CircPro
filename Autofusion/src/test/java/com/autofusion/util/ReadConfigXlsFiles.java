package com.autofusion.util;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;

@SuppressWarnings({"rawtypes","unused"})
public class ReadConfigXlsFiles {

	private Map<String, String> programSpecificData;
	Map<String, HashMap<String, String>> orMap;
	private Map<String, String> enviromentMap;
	private Map<String, HashMap<String, String>> globalVariableMap;
	private Map<String, HashMap> masterMap = new HashMap<String, HashMap>();;

	/**
	 * This method will read the program specific data sheet for current program
	 * 
	 * Input in the "data" column of component sheet "PSD|DataVAriable"
	 */

	/**
	 * Read Object Repository
	 * 
	 * @param folderLocation
	 * @param APP_LOGS
	 */
	public Map<String, HashMap<String, String>> readObjectRepository(String folderLocation, Logger APP_LOGS) {
		orMap = new HashMap<String, HashMap<String, String>>();

		APP_LOGS.info("Reading Object Repository");

		try {
			Xls_Reader webORXls = new Xls_Reader(folderLocation + "/OR.xlsx");
			for (int row = 2; row <= webORXls.getRowCount("OR"); row++) {
				// HashMap<String, String> detailMap1= new TreeMap();
				LinkedHashMap<String, String> detailMap = new LinkedHashMap<String, String>();
				String key = webORXls.getCellData("OR", "Variable Name", row);
				// if(!webORXls.getCellData("OR","xpathChrome", row).equals(""))
				detailMap.put("xpathChrome", webORXls.getCellData("OR", "xpathChrome", row));
				// if(!webORXls.getCellData("OR", "id", row).equals(""))
				detailMap.put("id", webORXls.getCellData("OR", "id", row));
				// if(!webORXls.getCellData("OR", "name", row).equals(""))
				detailMap.put("name", webORXls.getCellData("OR", "name", row));
				// if(!webORXls.getCellData("OR", "css", row).equals(""))
				detailMap.put("css", webORXls.getCellData("OR", "css", row));
				// if(!webORXls.getCellData("OR", "xpath", row).equals(""))
				detailMap.put("xp", webORXls.getCellData("OR", "xpath", row));
				// if(!webORXls.getCellData("OR", "tagName", row).equals(""))
				detailMap.put("tagName", webORXls.getCellData("OR", "tagName", row));
				// if(!webORXls.getCellData("OR", "link", row).equals(""))
				detailMap.put("link", webORXls.getCellData("OR", "link", row));
				// if(!webORXls.getCellData("OR", "type", row).equals(""))
				detailMap.put("type", webORXls.getCellData("OR", "type", row));

				orMap.put(key, detailMap);
			}
		} catch (Exception e) {
			APP_LOGS.debug("Error in reading object repository" + e);
		}

		setOrMap(orMap);
		return orMap;
	}

	public Map<String, String> readConfigurations(Xls_Reader webORXls, Map<String, String> configurationMap,
			Logger APP_LOG) {
		APP_LOG.info("Reading Configurations");
		// Map<String,String> configurationMap = new HashMap<String, String>();;
		if (!configurationMap.isEmpty()) {
			System.out.println("isEmpty");
			return configurationMap;
		}

		try {
			// Xls_Reader webORXls = new
			// Xls_Reader(testCaseBasePath+"/Config.xlsx");
			for (int row = 2; row <= webORXls.getRowCount("Configurations"); row++) {
				String key = webORXls.getCellData("Configurations", "Key", row);
				String value = webORXls.getCellData("Configurations", "Value", row);
				configurationMap.put(key, value);
			}
		} catch (Exception e) {
			APP_LOG.debug("Error in reading object repository" + e);
		}

		return configurationMap;
	}

	public Map<String, String> readEmail(Xls_Reader webORXls, Map<String, String> configurationMap,
			String enviroment, Logger APP_LOGS) {
		APP_LOGS.info("Reading Email");
		try {
			for (int row = 2; row <= webORXls.getRowCount("Email"); row++) {
				String key = webORXls.getCellData("Email", "Runmode", row).toLowerCase();

				if (key.trim().equalsIgnoreCase("Y")) {
					configurationMap.put("UserName",
							webORXls.getCellData("Email", "UserName", row).toLowerCase());
					configurationMap.put("Password", webORXls.getCellData("Email", "Password", row));
					configurationMap.put("To",
							webORXls.getCellData("Email", "To", row).toLowerCase());
					configurationMap.put("CC", webORXls.getCellData("Email", "CC", row));
					configurationMap.put("BCC", webORXls.getCellData("Email", "BCC", row));
					configurationMap.put("SmtpHost", webORXls.getCellData("Email", "SmtpHost", row));
					configurationMap.put("SmtpPort", webORXls.getCellData("Email", "SmtpPort", row));
					configurationMap.put("Subject", webORXls.getCellData("Email", "Subject", row));
					configurationMap.put("Message", webORXls.getCellData("Email", "Message", row));
					
					break;
				}
			}
		} catch (Exception e) {
			APP_LOGS.debug("Error in reading readEnviromentsUrls" + e);
		}

		setEnviromentMap(configurationMap);
		return configurationMap;
	}

	public Map<String, String> readEnviromentsUrls(Xls_Reader webORXls, Map<String, String> configurationMap,
			String enviroment, Logger APP_LOGS) {
		APP_LOGS.info("Reading readEnviromentsUrls");
		// enviromentMap = new HashMap<String, String>();
		try {
			// Xls_Reader webORXls = new
			// Xls_Reader(testCaseBasePath+"/Config.xlsx");

			for (int row = 2; row <= webORXls.getRowCount("EnviromentConfig"); row++) {

				String key = webORXls.getCellData("EnviromentConfig", "Enviroment", row).toLowerCase();
				if (enviroment.trim().equalsIgnoreCase(key)) {
					configurationMap.put("Enviroment",
							webORXls.getCellData("EnviromentConfig", "Enviroment", row).toLowerCase());
					configurationMap.put("DomainUrl", webORXls.getCellData("EnviromentConfig", "DomainURL", row));
					configurationMap.put("DB_URL",
							webORXls.getCellData("EnviromentConfig", "DB_URL", row).toLowerCase());
					configurationMap.put("DB_USER", webORXls.getCellData("EnviromentConfig", "DB_USER", row));
					configurationMap.put("DB_PASS", webORXls.getCellData("EnviromentConfig", "DB_PASS", row));
				}
			}
		} catch (Exception e) {
			APP_LOGS.debug("Error in reading readEnviromentsUrls" + e);
		}

		setEnviromentMap(configurationMap);
		return configurationMap;
	}

	
	public Map<String, String> readConfigurationsXls(String testCaseBasePath, String enviroment, Logger APP_LOGS) {
		APP_LOGS.info("Reading " + testCaseBasePath + "\\Config.xlsx");
		Map<String, String> configurationMap = new HashMap<String, String>();
		;
		Xls_Reader webORXls = new Xls_Reader(testCaseBasePath + "\\Config.xlsx");

		configurationMap = readConfigurations(webORXls, configurationMap, APP_LOGS);

		if (webORXls.isSheetExist(enviroment.trim() + "Configurations")) {
			configurationMap = readEnvironmentConfiguration(webORXls, configurationMap, enviroment, APP_LOGS);
			System.out.println("Environment configuration file sucessfully readed");
		} else {
			APP_LOGS.debug("Environemnt Configuration sheet not exist");
			System.out.println("Environemnt Configuration sheet not exist");
		}

		configurationMap = readEnviromentsUrls(webORXls, configurationMap, enviroment, APP_LOGS);
		configurationMap = readEmail(webORXls, configurationMap, enviroment, APP_LOGS);

		return configurationMap;
	}

	/**
	 * Read environment related configuration elements and it will over right
	 * the configuration element. Priority will be given to
	 * EnvironmentConfiguration
	 * 
	 * @param webORXls
	 * @param configurationMap
	 * @param enviroment
	 * @param APP_LOGS
	 * @return
	 */

	public Map<String, String> readEnvironmentConfiguration(Xls_Reader webORXls, Map<String, String> configurationMap,
			String enviroment, Logger APP_LOGS) {
		APP_LOGS.info("Reading readEnviromentsUrls");
		try {
			for (int row = 2; row <= webORXls.getRowCount(enviroment + "Configurations"); row++) {
				String key = webORXls.getCellData(enviroment + "Configurations", "Key", row);
				String value = webORXls.getCellData(enviroment + "Configurations", "Value", row);
				configurationMap.put(key, value);
			}
		} catch (Exception e) {
			APP_LOGS.debug("Error in reading readEnviromentsUrls" + e);
		}

		setEnviromentMap(configurationMap);
		return configurationMap;
	}

	/**
	 * NewDone This function is used for the reading the global var from common
	 * and local sheet
	 * 
	 * @param xlsFileName
	 * @param getCellDataName
	 * 
	 * @return glsMap for local and globalVariableMap for common sheet
	 */
	public Map<String, HashMap<String, String>> readObjectGlobalVar(String xlsFileName, String getCellDataName,
			Logger APP_LOGS) {
		APP_LOGS.info("Reading global variable");
		globalVariableMap = new HashMap<String, HashMap<String, String>>();
		try {
			Xls_Reader webORXls = new Xls_Reader(xlsFileName + ".xlsx");
			for (int row = 2; row <= webORXls.getRowCount(getCellDataName.toString()); row++) {
				HashMap<String, String> detailMap = new HashMap<String, String>();
				String key = webORXls.getCellData(getCellDataName.toString(), "ElementID", row);
				String data = webORXls.getCellData(getCellDataName.toString(), "Data", row);

				detailMap.put("Data", webORXls.getCellData(getCellDataName.toString(), "Data", row));

				detailMap.put("AppendRandom", webORXls.getCellData(getCellDataName.toString(), "AppendRandom", row));
				globalVariableMap.put(key, detailMap);
			}
		} catch (Exception e) {
			APP_LOGS.debug(e);
		}

		setGlobalVariableMap(globalVariableMap);
		return globalVariableMap;
	}

	public Map<String, String> getProgramSpecificData() {
		return programSpecificData;
	}

	public void setProgramSpecificData(Map<String, String> programSpecificData) {
		this.programSpecificData = programSpecificData;
	}

	public Map<String, HashMap<String, String>> getOrMap() {
		return orMap;
	}

	public void setOrMap(Map<String, HashMap<String, String>> orMap) {
		this.orMap = orMap;
	}

	public Map<String, String> getEnviromentMap() {
		return enviromentMap;
	}

	public void setEnviromentMap(Map<String, String> enviromentMap) {
		this.enviromentMap = enviromentMap;
	}

	public Map<String, HashMap<String, String>> getGlobalVariableMap() {
		return globalVariableMap;
	}

	public void setGlobalVariableMap(Map<String, HashMap<String, String>> globalVariableMap) {
		this.globalVariableMap = globalVariableMap;
	}

	public String getGlobalVariableMapData(String data) {

		if (globalVariableMap.containsKey(data)) {
			data = globalVariableMap.get(data).toString();
			return data;
		}

		return data;
	}

	public String getProgramSpecificData(String data) {

		String psdKey = data.split("\\|")[1];
		if (programSpecificData.containsKey(psdKey)) {
			data = programSpecificData.get(psdKey);
			return data;
		}

		return data;
	}

	// Read Data from DataSheet
	public void readDataFromDataSheet(String testStepsCaseId, Xls_Reader currentSuiteXls) {// splitedData
																							// =
																							// Column
																							// name
		String tcaseId;
		masterMap.clear();
		for (int i = 2; i <= currentSuiteXls.getRowCount(Constants.DATA_SHEET); i++) {
			tcaseId = currentSuiteXls.getCellData(Constants.DATA_SHEET, 0, i);
			if (tcaseId.equalsIgnoreCase(testStepsCaseId)) {
				HashMap<String, String> rowData = new HashMap<String, String>();

				for (int j = 0; j < currentSuiteXls.getColumnCount(Constants.DATA_SHEET); j++) {

					String colHeading = currentSuiteXls.getCellData(Constants.DATA_SHEET, j, 1);
					rowData.put(colHeading, currentSuiteXls.getCellData(Constants.DATA_SHEET, colHeading, i));
				}
				masterMap.put(currentSuiteXls.getCellData(Constants.DATA_SHEET, Constants.COL_HEAD_TCDI, i), rowData);

			}
		}

	}

	public Map<String, HashMap> getMasterMap() {
		return masterMap;
	}

	public void setMasterMap(Map<String, HashMap> masterMap) {
		this.masterMap = masterMap;
	}

	public static String getSQLQuery(String testCaseBasePath, String sqlId, Logger APP_LOGS) {
		APP_LOGS.info("Reading Object Repository");
		String query = "";
		try {
			Xls_Reader webORXls = new Xls_Reader(testCaseBasePath + "/CommonSteps.xlsx");
			for (int row = 2; row <= webORXls.getRowCount("SQLQuery"); row++) {

				String sqlIdXls = webORXls.getCellData("SQLQuery", "SQLID", row).toLowerCase();
				if (sqlId.equalsIgnoreCase(sqlIdXls)) {
					query = webORXls.getCellData("SQLQuery", "Query", row).toLowerCase();
				}
			}
		} catch (Exception e) {
			APP_LOGS.debug("Error in reading object repository" + e);
		}

		return query;
	}

}
