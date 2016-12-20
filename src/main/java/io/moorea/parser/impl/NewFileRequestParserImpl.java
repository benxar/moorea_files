package io.moorea.parser.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.moorea.parser.IJsonParser;
import io.moorea.parser.request.NewFileRequest;

public class NewFileRequestParserImpl implements IJsonParser {

	@Override
	public Object parseJson(String toParse) throws Exception{
		try {
			JsonParser parser = new JsonParser(); 
			JsonObject obj = parser.parse(toParse).getAsJsonObject();    		
			///parseo de tipo de archivo
			String type = obj.get("type").getAsString();
			JsonObject header = obj.get("header").getAsJsonObject();
			///parseo de año
			int anio = header.get("year").getAsInt();
			String prefix = header.get("prefix").getAsString();
			///parseo de oficina
			JsonObject office = header.get("office").getAsJsonObject();
			String idOficina = office.get("id").getAsString();
			String textoOficina = office.get("text").getAsString();
			///parseo de categoria
			JsonObject category = header.get("category").getAsJsonObject();
			String idCategoria = category.get("id").getAsString();
			String textoCategoria = category.get("text").getAsString();
			NewFileRequest nfr = new NewFileRequest(type, idOficina, textoOficina, idCategoria, textoCategoria, anio, prefix);
			return nfr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
