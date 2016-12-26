package io.moorea.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.moorea.entity.UnassignedFile;
import io.moorea.model.JsonResult;
import io.moorea.persistence.DocumentFileDAO;
import io.moorea.persistence.RepositoryDatastore;
import io.moorea.persistence.UnassignedFileDAO;
import io.moorea.service.UnassignedFileRepositoryService;

@Service
public class UnassignedFileRepositoryServiceImpl implements UnassignedFileRepositoryService {

	@Autowired
	private UnassignedFileDAO ufArchive;

	@Override
	public UUID save(UnassignedFile uFile) {
		UUID result = null;
		try {
			if (RepositoryDatastore.getDatastore() != null){
				result = (UUID) RepositoryDatastore.getDatastore().save(uFile).getId();
				ufArchive.saveFile(uFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JsonResult getUnassignedFileById(UUID id) {
		JsonResult result = null;
		try {
			UnassignedFile auxR = new UnassignedFile();
			auxR.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResult(false, "There was an error while retrieving file");
		}
		return result;
	}

	@Override
	public JsonResult searchUnassignedFileByKeywords(List<String> keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonResult searchUnassignedFileByName(String name, String extension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonResult searchUnassignedFileByDateAdded(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonResult deleteUnassignedFile(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
