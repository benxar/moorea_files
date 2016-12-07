package io.moorea.persistence;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DocumentRepositoryDatastore {
	private DocumentRepositoryDatastore() {
	}
	private static Datastore datastore = null;
	private static DocumentRepositoryDatastore instance = null;

	public static Datastore getDatastore() {
		if (datastore == null) {
			synchronized (DocumentRepositoryDatastore.class) {
				if (instance == null) {
					instance = new DocumentRepositoryDatastore();
					if (!setDatastore())
						instance = null;
				}
			}
		}
		return datastore;
	}

	private static boolean setDatastore() {
		try {
			MongoClient client = new MongoClient();
			Morphia morphia = new Morphia();
			morphia.mapPackage("io.moorea.entity");
			datastore = morphia.createDatastore(client, "mydb");
			datastore.ensureIndexes();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			datastore = null;
			return false;
		}
	}
}
