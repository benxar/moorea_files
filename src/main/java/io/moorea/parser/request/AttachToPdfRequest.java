package io.moorea.parser.request;

import java.util.ArrayList;
import java.util.List;

import io.moorea.entity.Attachment;

public class AttachToPdfRequest {
	private String b64;
	private List<Attachment> lAttach = new ArrayList<Attachment>();
	public String getB64() {
		return b64;
	}
	public void setB64(String b64) {
		this.b64 = b64;
	}
	public List<Attachment> getlAttach() {
		return lAttach;
	}
	public void setlAttach(List<Attachment> lAttach) {
		this.lAttach = lAttach;
	}
	public void addAtacchment(Attachment toAdd){
		lAttach.add(toAdd);
	}
	public void removeAtacchment(Attachment toRemove){
		lAttach.remove(toRemove);
	}
}
