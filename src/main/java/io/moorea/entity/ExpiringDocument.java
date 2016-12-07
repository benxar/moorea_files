package io.moorea.entity;

import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity("ExpiringDocument")
public class ExpiringDocument {
    @Id
    private ObjectId id;
    @Indexed
    private UUID parentDocument;
    @Indexed
    private int number;
	@Indexed( expireAfterSeconds = 60*60 )//expires in one hour
	private Date expiringDate;
	
	public ExpiringDocument(){
		expiringDate = new Date(System.currentTimeMillis());
	}
	
	public ObjectId getId() {
		return id;
	}
	public UUID getParentDocument() {
		return parentDocument;
	}
	public void setParentDocument(UUID parentDocument) {
		this.parentDocument = parentDocument;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
