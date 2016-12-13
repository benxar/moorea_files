package io.moorea.persistence.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.apache.coyote.http11.filters.BufferedInputFilter;

import io.moorea.entity.DocumentFile;
import io.moorea.parser.request.FilePostRequest;
import io.moorea.persistence.DocumentFileDAO;

public class DocumentFileDAOImpl implements DocumentFileDAO {

	@Override
	public boolean saveFile(UUID fileId, int number, FilePostRequest fpr) {
		String fileName = "";
		try {
			byte[] data = Base64.getDecoder().decode(fpr.getB64());
			fileName = fileId.toString() + "_" + number + ".pdf";
			try (OutputStream stream = new FileOutputStream(path + "/" + fileName)) {
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
	public DocumentFile retrieveFile(UUID fileId, int number) {
		DocumentFile result = null;
		String fileName = "";
		try {
			fileName = fileId.toString() + "_" + number + ".pdf";
			File f = new File(fileName);
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);
				BufferedInputStream bis = new BufferedInputStream(fis);
				byte[] arr = new byte[(int) f.length()];
				bis.read(arr);
				String b64 = Base64.getEncoder().encodeToString(arr);
				result = new DocumentFile(fileId.toString(), String.valueOf(number), "", b64);
				bis.close();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteFile(UUID fileId, int number) {
		String fileName = "";
		try {
			fileName = fileId.toString() + "_" + number + ".pdf";
			File f = new File(fileName);
			if (f.exists()) {
				return f.delete();
			} else
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
