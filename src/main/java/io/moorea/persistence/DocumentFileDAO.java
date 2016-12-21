package io.moorea.persistence;

import java.util.UUID;

import io.moorea.entity.DocumentFile;
import io.moorea.parser.request.FilePostRequest;

public interface DocumentFileDAO {

	public boolean saveFile(UUID fileId, int number, FilePostRequest fpr);

	public DocumentFile retrieveFile(UUID fileId, int number);

	public boolean deleteFile(UUID fileId, int number);
}
