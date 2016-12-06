package io.moorea.persistence;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mongodb.MongoClient;

import io.moorea.entity.*;
import io.moorea.model.JsonResult;

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
			morphia.mapPackage("io.moorea.entity");
			datastore = morphia.createDatastore(client, "mydb");
			datastore.ensureIndexes();
		} catch (Exception e) {
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
	
	public JsonResult getDocuments(String type,int page,int limit,String order_filed,String order_direction){
		JsonResult toReturn = null;
		try {
			Query<Document> query = datastore.createQuery(Document.class);
			List<Document> result = query.field("type").equal(type).order((order_direction.compareToIgnoreCase("asc")==0?"":"-")+order_filed).offset(page).limit(limit).asList();
			if(result!=null && result.size()>0)
				toReturn = new JsonResult(true, "Success",result);
			else
				toReturn = new JsonResult(false, "No results where found");
		} catch (Exception e) {
			toReturn = new JsonResult(false, "Error while performing search");
		}
		return toReturn;
	}
	
	public JsonResult getDocumentById(UUID id){
		JsonResult toReturn = null;
		try {
			Query<Document> query = datastore.createQuery(Document.class);
			Document result = query.field("id").equal(id).get();
			if(result!=null)
				toReturn = new JsonResult(true, "Success",result);
			else
				toReturn = new JsonResult(false, "No result was found");
		} catch (Exception e) {
			toReturn = new JsonResult(false, "Error while performing search");
		}
		return toReturn;
	}
	
	public JsonResult getDocumentFileById(UUID id,String fileId){
		try {
			Query<Document> q = datastore.createQuery(Document.class);
			Document auxResult = q.field("id").equal(id).field("files.doc_id").equal(fileId).project("files" , true).get();
			if(auxResult != null)
				for(DocumentFile aux : auxResult.getFiles())
					if(aux.getDoc_id()==fileId)
						return new JsonResult(true, "Success",aux);	
			return new JsonResult(false, "No result was found");
		} catch (Exception e) {
			return new JsonResult(false, "Error while performing search");
		}
	}
}
