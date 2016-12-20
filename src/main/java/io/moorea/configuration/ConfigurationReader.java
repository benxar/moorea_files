package io.moorea.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	public boolean getConfiguration() throws IOException {
		boolean result = false;
		InputStream inputStream = null;
		try {
			Configuration conf = Configuration.getInstance();
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			String dbName = prop.getProperty("dbName");
			String dbConnUrl = prop.getProperty("dbConnUrl");
			String dbUser = prop.getProperty("dbUser");
			String dbPassword = prop.getProperty("dbPassword");
			String expireAfterSeconds = prop.getProperty("expireAfterSeconds");
			conf.setDbName(dbName);
			conf.setDbConnUrl(dbConnUrl);
			conf.setDbPassword(dbPassword);
			conf.setDbUser(dbUser);
			conf.setExpireAfterSeconds(Integer.valueOf(expireAfterSeconds));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result= false;
		} finally {
			if (inputStream != null) {
				inputStream.close();	
			}
		}
		return result;
	}
}
