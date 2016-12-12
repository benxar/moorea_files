package io.moorea.parser.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.FilePostRequest;

public class FilePostRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception {
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();
			///parseo de nombre del archivo
			String name = obj.get("name").getAsString();
			///parseo del archivo en b64
			String b64 = obj.get("b64").getAsString();
			FilePostRequest fpr = new FilePostRequest(name, b64);
			return fpr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
