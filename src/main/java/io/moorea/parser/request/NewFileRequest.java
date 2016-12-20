package io.moorea.parser.request;

public class NewFileRequest {
	String type;
	String officeId;
	String officeText;
	String categoryId;
	String categoryText;
	int year;
	String prefix;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeText() {
		return officeText;
	}

	public void setOfficeText(String officeText) {
		this.officeText = officeText;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryText() {
		return categoryText;
	}

	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public NewFileRequest(String type, String officeId, String officeText, String categoryId, String categoryText,
			int year, String prefix) {
		super();
		this.type = type;
		this.officeId = officeId;
		this.officeText = officeText;
		this.categoryId = categoryId;
		this.categoryText = categoryText;
		this.year = year;
		this.prefix = prefix;
	}
}
