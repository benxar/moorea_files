package io.moorea.entity;

import java.util.UUID;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class DocumentFile {
	private UUID parent;
	private int doc_id;
	private String name;
	private String path;
	@Transient
	private String b64;

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
		return path;
	}
	public void setB64(String b64) {
		this.path = b64;
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
