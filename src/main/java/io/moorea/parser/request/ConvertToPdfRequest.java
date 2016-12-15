package io.moorea.parser.request;

public class ConvertToPdfRequest {
	private String origin;
	private String htlm;
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getHtlm() {
		return htlm;
	}
	public void setHtlm(String htlm) {
		this.htlm = htlm;
	}
	public ConvertToPdfRequest(String origin, String htlm) {
		super();
		this.origin = origin;
		this.htlm = htlm;
	}
}
