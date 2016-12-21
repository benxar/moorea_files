package io.moorea.configuration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	public boolean getConfiguration() throws Exception {
		boolean result = false;
		InputStream inputStream = null;
		try {
			Configuration conf = Configuration.getInstance();
			Properties prop = new Properties();
			String propFileName = "moorea_files.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			String dbName = prop.getProperty("dbName");
			String dbConnUrl = prop.getProperty("dbConnUrl");
			String dbConnPort = prop.getProperty("dbConnPort");
			String dbUser = prop.getProperty("dbUser");
			String dbPassword = prop.getProperty("dbPassword");
			String expireAfterSeconds = prop.getProperty("expireAfterSeconds");
			String fsRoute = prop.getProperty("fsRoute");
			conf.setDbName(dbName);
			conf.setDbConnUrl(dbConnUrl);
			conf.setDbConnPort(Integer.valueOf(dbConnPort));
			conf.setDbPassword(dbPassword);
			conf.setDbUser(dbUser);
			conf.setExpireAfterSeconds(Integer.valueOf(expireAfterSeconds));
			conf.setFsRoute(fsRoute);
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
