package io.moorea.parser.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.ValidatePdfRequest;

public class ValidatePdfRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception {
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();    		
			///parseo de string en base 64
			String b64 = obj.get("b64").getAsString();
			ValidatePdfRequest vpr = new ValidatePdfRequest(b64);
			return vpr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
