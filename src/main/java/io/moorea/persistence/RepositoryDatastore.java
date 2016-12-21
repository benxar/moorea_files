package io.moorea.persistence;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import io.moorea.configuration.Configuration;

public class RepositoryDatastore {
	private RepositoryDatastore() {
	}
	private static Datastore datastore = null;
	private static RepositoryDatastore instance = null;

	public static Datastore getDatastore() {
		if (datastore == null) {
			synchronized (RepositoryDatastore.class) {
				if (instance == null) {
					instance = new RepositoryDatastore();
					if (!setDatastore())
						instance = null;
				}
			}
		}
		return datastore;
	}

	private static boolean setDatastore() {
		try {
			MongoClient client = new MongoClient(Configuration.getInstance().getDbConnUrl(),Configuration.getInstance().getDbConnPort());
			Morphia morphia = new Morphia();
			morphia.mapPackage("io.moorea.entity");
			datastore = morphia.createDatastore(client, Configuration.getInstance().getDbName());
			datastore.ensureIndexes();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			datastore = null;
			return false;
		}
	}
}
