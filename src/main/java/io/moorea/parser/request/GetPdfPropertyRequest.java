package io.moorea.parser.request;

public class GetPdfPropertyRequest {
	private String property;
	private String b64;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public GetPdfPropertyRequest(String property, String b64) {
		super();
		this.property = property;
		this.b64 = b64;
	}

}
