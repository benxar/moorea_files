package io.moorea.entity;

import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import io.moorea.model.ExpiringDocumentErrorCode;

@Entity("ExpiringDocument")
public class ExpiringDocument {
    @Id
    private ObjectId id;
    @Indexed
    private UUID parentDocument;
    @Indexed
    private int number;
    private UUID key;
	@Indexed( expireAfterSeconds = 60*60 )//expires in one hour
	private Date expiringDate;
	@Transient
	private ExpiringDocumentErrorCode errorCode;
	
	public ExpiringDocument(){
		expiringDate = new Date(System.currentTimeMillis());
		setKey(UUID.randomUUID());
		setErrorCode(ExpiringDocumentErrorCode.NO_ERROR);
	}
	public ExpiringDocument(UUID key){
		expiringDate = new Date(System.currentTimeMillis());
		setKey(key);
		setErrorCode(ExpiringDocumentErrorCode.NO_ERROR);
	}
	public ExpiringDocument(ExpiringDocumentErrorCode error){
		setErrorCode(error);
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
	public UUID getKey() {
		return key;
	}
	private void setKey(UUID key) {
		this.key = key;
	}
	public ExpiringDocumentErrorCode getErrorCode() {
		return errorCode;
	}
	private void setErrorCode(ExpiringDocumentErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
