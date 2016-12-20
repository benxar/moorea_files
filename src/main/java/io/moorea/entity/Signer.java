package io.moorea.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Signer {
	private String name;
	private CertificateSubject ca;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CertificateSubject getCa() {
		return ca;
	}

	public void setCa(CertificateSubject ca) {
		this.ca = ca;
	}

	public Signer(String name, CertificateSubject ca) {
		super();
		this.name = name;
		this.ca = ca;
	}

	public Signer() {
	}
}
