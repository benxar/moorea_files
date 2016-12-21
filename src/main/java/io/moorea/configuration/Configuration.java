package io.moorea.configuration;

public class Configuration {
	private static Configuration instance = null;

	private Configuration() {
	}

	public static Configuration getInstance() {
		if (instance == null) {
			synchronized (Configuration.class) {
				if (instance == null) {
					instance = new Configuration();
				}
			}
		}
		return instance;
	}

	private String dbName;
	private String dbConnUrl;
	private int dbConnPort;
	private String dbUser;
	private String dbPassword;
	private int expireAfterSeconds;
	private String fsRoute;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbConnUrl() {
		return dbConnUrl;
	}

	public void setDbConnUrl(String dbConnUrl) {
		this.dbConnUrl = dbConnUrl;
	}

	public int getDbConnPort() {
		return dbConnPort;
	}

	public void setDbConnPort(int dbConnPort) {
		this.dbConnPort = dbConnPort;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public int getExpireAfterSeconds() {
		return expireAfterSeconds;
	}

	public void setExpireAfterSeconds(int expireAfterSeconds) {
		this.expireAfterSeconds = expireAfterSeconds;
	}
	
	public String getFsRoute() {
		return fsRoute;
	}

	public void setFsRoute(String fsRoute) {
		this.fsRoute = fsRoute;
	}
}
