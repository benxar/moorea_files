package io.moorea.persistence.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import io.moorea.configuration.Configuration;
import io.moorea.entity.UnassignedFile;
import io.moorea.persistence.UnassignedFileDAO;

public class UnassignedFileDAOImpl implements UnassignedFileDAO {

	private String path = "";
	
	public UnassignedFileDAOImpl() {
		path = Configuration.getInstance().getFsRoute() + "/unassigned/";
		File folder = new File("unassigned");
		if(!folder.exists() || !folder.isDirectory())
			folder.mkdir();
	}
	
	@Override
	public boolean saveFile(UnassignedFile toSave) {
		try {
			byte[] data = Base64.getDecoder().decode(toSave.getB64());
			try (OutputStream stream = new FileOutputStream(path + toSave.getName())) {
				stream.write(data);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public UnassignedFile retrieveFile(UUID fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteFile(UUID fileId) {
		try {
			File aux = new File(path + fileId.toString());
			if(aux.exists())
				return aux.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
