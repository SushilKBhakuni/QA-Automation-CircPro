package com.autofusion;

import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unused")
public class PropertyManager {

	public static PropertyManager _instance = null;
	public Properties testData;
	InputStream fileConfig = null;
	public PropertyManager(){
		 
	        try {
	        	testData = new Properties();
	        	Thread currentThread = Thread.currentThread();
	        	ClassLoader contextClassLoader = currentThread.getContextClassLoader();
	        	fileConfig = getClass().getClassLoader().getResourceAsStream("config.properties");
	        	if (fileConfig != null) 
	        	{
	        		testData.load(fileConfig);
	        		fileConfig.close();
			    }
	        	else
	        	{
	        		 System.out.println("PropertyManager Error on reading config file");
	        		 fileConfig.close();
	        	}
	        
	        }	
				
		catch (Exception e) 
	        
	        {
	            System.out.println("Error" + e);
	        }
	        
	}
	
	
	 public static PropertyManager getInstance() {

	        if (_instance == null) {
	            _instance = new PropertyManager();
	        }
	        return _instance;
    }

	 public String getValueForKey(String key) {
	        return testData.getProperty(key);
	        
	 }
	 
	 /**
		 * valueFromConfig return value from CONFIG properties if value from Jenkins
		 * environment is null
		 * 
		 * @return--> The value from config file or from jenkins varibale
		 */
		public  String valueFromConfig(String key) {

			// Checking if value from Jenkins variable is null, if it is null then
			// value from config.properties will return
			

				if (System.getenv(key) == null) {
					//APP_LOG.debug("Value of Jenkins parameter " + key + " is null, loaded value: "
							//+ CONFIG.getProperty(key) + " from Config properties");
					System.out.println("Value of Jenkins parameter " + key + " is null, loaded value: "
							+ getValueForKey(key) + " from Config properties");
					return getValueForKey(key);

				}

				System.out.println("Value of Jenkins parameter " + key + " is not null, loaded value: "
						+ System.getenv(key) + " from jenkins");
			return System.getenv(key);

		}
	 
	 
	
}
