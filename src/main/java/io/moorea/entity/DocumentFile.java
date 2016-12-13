package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DocumentFile {
	private String parent;
	private String doc_id;
	private String name;
	private String b64;

	public String getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public DocumentFile(String parent, String doc_id, String name, String b64) {
		super();
		this.parent = parent;
		this.doc_id = doc_id;
		this.name = name;
		this.b64 = b64;
	}

	public DocumentFile() {
	}
}
