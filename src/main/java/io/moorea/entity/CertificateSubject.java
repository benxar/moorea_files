package io.moorea.entity;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class CertificateSubject {
	private String email;
	private String cn;
	private String ou;
	private String o;
	private String l;
	private String st;
	private String serialnumber;
	private String c;
	private String t;
	private String uid;
	private String dn_qualifier;
	private String uidNumber;

	public String getUidNumber() {
		return uidNumber;
	}

	public void setUidNumber(String uidNumber) {
		this.uidNumber = uidNumber;
	}

	public String getDn_qualifier() {
		return dn_qualifier;
	}

	public void setDn_qualifier(String dn_qualifier) {
		this.dn_qualifier = dn_qualifier;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public CertificateSubject(X509Certificate x509Certificate) {
		X500Name x500name = null;
		try {
			x500name = new JcaX509CertificateHolder(x509Certificate).getSubject();
		} catch (CertificateEncodingException e) {
			// TODO
			e.printStackTrace();
		}
		try {
			this.cn = IETFUtils.valueToString(x500name.getRDNs(BCStyle.CN)[0].getFirst().getValue());
		} catch (Exception e) {
			this.cn = "";
		}
		try {
			this.o = IETFUtils.valueToString(x500name.getRDNs(BCStyle.O)[0].getFirst().getValue());
		} catch (Exception e) {
			this.o = "";
		}
		try {
			this.ou = IETFUtils.valueToString(x500name.getRDNs(BCStyle.OU)[0].getFirst().getValue());
		} catch (Exception e) {
			this.ou = "";
		}
		try {
			this.email = IETFUtils.valueToString(x500name.getRDNs(BCStyle.EmailAddress)[0].getFirst().getValue());
		} catch (Exception e) {
			this.email = "";
		}
		try {
			this.l = IETFUtils.valueToString(x500name.getRDNs(BCStyle.L)[0].getFirst().getValue());
		} catch (Exception e) {
			this.l = "";
		}
		try {
			this.st = IETFUtils.valueToString(x500name.getRDNs(BCStyle.ST)[0].getFirst().getValue());
		} catch (Exception e) {
			this.st = "";
		}
		try {
			this.serialnumber = IETFUtils
					.valueToString(x500name.getRDNs(BCStyle.SERIALNUMBER)[0].getFirst().getValue());
		} catch (Exception e) {
			this.serialnumber = "";
		}
		try {
			this.c = IETFUtils.valueToString(x500name.getRDNs(BCStyle.C)[0].getFirst().getValue());
		} catch (Exception e) {
			this.c = "";
		}
		try {
			this.t = IETFUtils.valueToString(x500name.getRDNs(BCStyle.T)[0].getFirst().getValue());
		} catch (Exception e) {
			this.t = "";
		}
		try {
			this.uid = IETFUtils.valueToString(x500name.getRDNs(BCStyle.UID)[0].getFirst().getValue());
		} catch (Exception e) {
			this.uid = "";
		}
		try {
			this.dn_qualifier = IETFUtils
					.valueToString(x500name.getRDNs(BCStyle.DN_QUALIFIER)[0].getFirst().getValue());
		} catch (Exception e) {
			this.dn_qualifier = "";
		}
		try {
			this.uidNumber = IETFUtils.valueToString(
					x500name.getRDNs(BCStyle.INSTANCE.attrNameToOID("1.3.6.1.1.1.1.0"))[0].getFirst().getValue());
		} catch (Exception e) {
			this.uidNumber = "";
		}
	}

	public CertificateSubject(X500Principal subjectX500Principal) {
		// TODO Auto-generated constructor stub
	}

	public CertificateSubject() {}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}
}