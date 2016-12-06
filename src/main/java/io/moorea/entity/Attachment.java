package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Attachment {
	private String id;
	private String name;
	private String path;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Attachment(String id, String name, String path) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
	}
	public Attachment() {
		super();
	}
}
