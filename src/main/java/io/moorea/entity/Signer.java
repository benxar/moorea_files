package io.moorea.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Signer {
	private String name;
	private CertificateSubject ca;
	private Date signDate;

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

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Signer(String name, CertificateSubject ca, Date signDate) {
		super();
		this.name = name;
		this.ca = ca;
		this.signDate = signDate;
	}

	public Signer() {
	}
}
