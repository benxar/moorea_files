package io.moorea.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

	public boolean getConfigurationFromParameters() {
		boolean result = true;
		try {
			Configuration conf = Configuration.getInstance();
			String dbName = System.getProperty("dbName");
			String dbConnUrl = System.getProperty("dbConnUrl");
			String dbConnPort = System.getProperty("dbConnPort");
			String dbUser = System.getProperty("dbUser");
			String dbPassword = System.getProperty("dbPassword");
			String expireAfterSeconds = System.getProperty("expireAfterSeconds");
			String fsRoute = System.getProperty("fsRoute");
			if (dbName != null)
				conf.setDbName(dbName);
			else
				result = false;
			if (dbConnUrl != null)
				conf.setDbConnUrl(dbConnUrl);
			else
				result = false;
			if (dbConnPort != null)
				conf.setDbConnPort(Integer.valueOf(dbConnPort));
			else
				result = false;
			if (dbPassword != null)
				conf.setDbPassword(dbPassword);
			else
				result = true;
			if (dbUser != null)
				conf.setDbUser(dbUser);
			else
				result = false;
			if (expireAfterSeconds != null)
				conf.setExpireAfterSeconds(Integer.valueOf(expireAfterSeconds));
			else
				result = false;
			if (fsRoute != null)
				conf.setFsRoute(fsRoute);
			else
				result = false;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getConfigurationFromFile() {
		boolean result = false;
		InputStream inputStream = null;
		try {
			Configuration conf = Configuration.getInstance();
			Properties prop = new Properties();
			String propFileName = "moorea_files.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} /*
				 * else { throw new FileNotFoundException("property file '" +
				 * propFileName + "' not found in the classpath"); }
				 */
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
			result = false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
