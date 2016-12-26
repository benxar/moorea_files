package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class Attachment {
	private String name;
	private String extension;
	@Transient
	private String b64;

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

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public Attachment(String name, String extension) {
		super();
		this.name = name;
		this.extension = extension;
	}

	public Attachment() {
		super();
	}
}
