package io.moorea.parser.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.ConvertToPdfRequest;

public class ConvertToPdfRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception {
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();
			///parseo de campo origen
			String origin = obj.get("origin").getAsString();
			///parseo del campo html que contiene el codigo
			String html = obj.get("html").getAsString();
			ConvertToPdfRequest ctpr = new ConvertToPdfRequest(origin, html);
			return ctpr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
