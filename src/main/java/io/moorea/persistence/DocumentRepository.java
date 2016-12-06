package io.moorea.persistence;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import io.moorea.entity.*;

public class DocumentRepository {
	
	private static DocumentRepository instance = null;
	
	public static DocumentRepository getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (DocumentRepository.class) {
				if (instance == null) {
					instance = new DocumentRepository();
					setDatastore();
				}
			}
		}
		return instance;
	}
	
	private static Datastore datastore;
	
	private static void setDatastore(){
		try {
			MongoClient client = new MongoClient();
			Morphia morphia = new Morphia();
			morphia.mapPackage("entity");
			datastore = morphia.createDatastore(client, "mydb");
			datastore.ensureIndexes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			datastore = null;
		}
	}
	
	private DocumentRepository() { }
	
	public Key<Document> save(Document document) {
		if(datastore!=null)
			return datastore.save(document);
		else
			return null;
	}
	
	public void getDocuments(String type,int page,int limit,String order_filed,String order_direction){
		Query<Document> query = datastore.createQuery(Document.class);
		query.field("type").equal(type).order((order_direction.compareToIgnoreCase(""))+order_filed);
	}
}
