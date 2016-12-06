package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Signer {
	private String name;
	private String ca;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCa() {
		return ca;
	}
	public void setCa(String ca) {
		this.ca = ca;
	}
}
