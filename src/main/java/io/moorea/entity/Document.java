package io.moorea.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.*;

@Entity("Documents")
public class Document {
    @Id
    private UUID id;
    @Indexed
    private String type;
	@Embedded
	private Category category;
	@Embedded
	private Office office;
	@Indexed
	private int year;
	private int updates;
	private Date lastUpdate;
	private String prefix;
	@Embedded
	private List<DocumentFile> files = new ArrayList<>();
	
	public void generateRandomId(){
		id = UUID.randomUUID();
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getUpdates() {
		return updates;
	}
	public void setUpdates(int updates) {
		this.updates = updates;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public List<DocumentFile> getFiles() {
		return files;
	}
	public void setFiles(List<DocumentFile> files) {
		this.files = files;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
