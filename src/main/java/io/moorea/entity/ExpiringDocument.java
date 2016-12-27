package io.moorea.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import io.moorea.configuration.Configuration;
import io.moorea.enums.ExpiringDocumentErrorCode;

@Entity("ExpiringDocument")
public class ExpiringDocument {
	@Id
	private ObjectId id;
	@Indexed
	private UUID parentDocument;
	@Indexed
	private int number;
	private UUID key;
	@Indexed(expireAfterSeconds = 0)
	private Date expiringDate;
	@Transient
	private ExpiringDocumentErrorCode errorCode;
	@Transient
	private String b64;
	private List<Signer> signers = new ArrayList<>();

	public ExpiringDocument() {
		expiringDate = new Date(System.currentTimeMillis() + (Configuration.getInstance().getExpireAfterSeconds()*1000));
		setKey(UUID.randomUUID());
		setErrorCode(ExpiringDocumentErrorCode.NO_ERROR);
	}

	public ExpiringDocument(UUID key) {
		expiringDate = new Date(System.currentTimeMillis());
		setKey(key);
		setErrorCode(ExpiringDocumentErrorCode.NO_ERROR);
	}

	public ExpiringDocument(ExpiringDocumentErrorCode error) {
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

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public List<Signer> getSigners() {
		return signers;
	}

	public void setSigners(List<Signer> signers) {
		this.signers = signers;
	}
}
