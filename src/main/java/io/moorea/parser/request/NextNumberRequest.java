package io.moorea.parser.request;

public class NextNumberRequest {
	private String b64;

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public NextNumberRequest(String b64) {
		super();
		this.b64 = b64;
	}
	
}
