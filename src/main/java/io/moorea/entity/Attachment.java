package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Attachment {
	private String id;
	private String name;
	private String extension;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String path) {
		this.extension = path;
	}
	public Attachment(String id, String name, String extension) {
		super();
		this.id = id;
		this.name = name;
		this.extension = extension;
	}
	public Attachment() {
		super();
	}
}
