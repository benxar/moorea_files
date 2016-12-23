package io.moorea.persistence;

import java.util.UUID;

import io.moorea.entity.UnassignedFile;

public interface UnassignedFileDAO {
	public boolean saveFile(UnassignedFile toSave);

	public UnassignedFile retrieveFile(UUID fileId);

	public boolean deleteFile(UUID fileId);
}
