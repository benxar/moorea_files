package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Category {
	private String id;
	private String text;
	public String getId() {
		return id;
	}
	public void setId(String categoryId) {
		this.id = categoryId;
	}
	public String getText() {
		return text;
	}
	public void setText(String categoryText) {
		this.text = categoryText;
	}
	public Category(String categoryId, String categoryText) {
		super();
		this.id = categoryId;
		this.text = categoryText;
	}
}
