package io.moorea.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

@Entity("UnassignedFile")
public class UnassignedFile {
	@Id
	private UUID id;
	@Indexed
	private Date dateAdded;
	@Indexed
	private String name;
	@Indexed
	private String extension;
	@Indexed
	private List<String> keyWords;
	@Transient
	private String b64;

	public void generateRandomId() {
		id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	public String getB64() {
		return b64;
	}

	public void setB64(String b64) {
		this.b64 = b64;
	}

	public void addKeyWord(String keyWord){
		this.keyWords.add(keyWord);
	}
	
	public void removeKeyWord(String keyWord){
		this.keyWords.remove(keyWord);
	}
}
