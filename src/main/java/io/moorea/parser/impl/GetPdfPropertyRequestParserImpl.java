package io.moorea.parser.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.GetPdfPropertyRequest;

public class GetPdfPropertyRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception {
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();
			///parseo de la propiedad a buscar
			String property = obj.get("property").getAsString();
			///parseo del archivo en b64
			String b64 = obj.get("b64").getAsString();
			GetPdfPropertyRequest fpr = new GetPdfPropertyRequest(property, b64);
			return fpr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
