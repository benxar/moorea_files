package io.moorea.parser.request;

public class ValidatePdfRequest {
	private String b64;

	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public ValidatePdfRequest(String b64) {
		super();
		this.b64 = b64;
	}
}