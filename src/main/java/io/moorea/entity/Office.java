package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Office {
	private String id;
	private String text;
	public String getId() {
		return id;
	}
	public void setId(String officeId) {
		this.id = officeId;
	}
	public String getText() {
		return text;
	}
	public void setText(String officeText) {
		this.text = officeText;
	}
	public Office(String officeId, String officeText) {
		super();
		this.id = officeId;
		this.text = officeText;
	}
	public Office() {
		super();
	}
}
