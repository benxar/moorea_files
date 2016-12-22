package io.moorea.parser.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import io.moorea.entity.Attachment;
import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.AttachToPdfRequest;

public class AttachToPdfRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception {
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();    		
			///parseo de string en base 64
			String b64 = obj.get("b64").getAsString();
			Type listType = new TypeToken<List<Attachment>>() {}.getType();
			List<Attachment> lAtt = new Gson().fromJson(obj.get("atachments"), listType);
			AttachToPdfRequest atpr = new AttachToPdfRequest();
			atpr.setB64(b64);
			atpr.setlAttach(lAtt);
			return atpr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
