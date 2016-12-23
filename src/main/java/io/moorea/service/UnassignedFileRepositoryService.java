package io.moorea.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.moorea.entity.UnassignedFile;
import io.moorea.model.JsonResult;

public interface UnassignedFileRepositoryService {
	public UUID save(UnassignedFile uFile);

	public JsonResult getUnassignedFileById(UUID id);

	public JsonResult searchUnassignedFileByKeywords(List<String> keywords);

	public JsonResult searchUnassignedFileByName(String name, String extension);

	public JsonResult searchUnassignedFileByDateAdded(Date fromDate, Date toDate);

	public JsonResult deleteUnassignedFile(UUID id);
}