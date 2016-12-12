package io.moorea.persistence;

import java.util.UUID;

import io.moorea.entity.DocumentFile;
import io.moorea.parser.request.FilePostRequest;

public class DocumentFileDAOImpl implements DocumentFileDAO {

	@Override
	public boolean saveFile(UUID fileId, int number, FilePostRequest fpr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentFile retrieveFile(UUID fileId, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteFile(UUID fileId, int number) {
		// TODO Auto-generated method stub
		return false;
	}

}
