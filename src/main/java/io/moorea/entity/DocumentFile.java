package io.moorea.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class DocumentFile {
	private UUID parent;
	private int doc_id;
	private String name;
	private String path;
	private List<Attachment> attachments = new ArrayList<>();
	@Transient
	private String b64;
	@Transient
	private List<Signer> signers;

	public int getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}

	public UUID getParent() {
		return parent;
	}

	public void setParent(UUID parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
	
	public void addSingner(Signer toAdd){
		this.signers.add(toAdd);
	}
	
	public void removeSingner(Signer toRemove){
		this.signers.remove(toRemove);
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(Attachment toAdd){
		this.attachments.add(toAdd);
	}
	
	public void removeSingner(Attachment toRemove){
		this.attachments.remove(toRemove);
	}

	public DocumentFile(UUID parent, int doc_id, String name, String path) {
		super();
		this.parent = parent;
		this.doc_id = doc_id;
		this.name = name;
		this.path = path;
	}

	public DocumentFile() {
	}
}
