package io.moorea.parser.request;

public class FilePostRequest {
	private String name;
	private String b64;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getB64() {
		return b64;
	}
	public void setB64(String b64) {
		this.b64 = b64;
	}
	public FilePostRequest(String name, String b64) {
		super();
		this.name = name;
		this.b64 = b64;
	}
}
