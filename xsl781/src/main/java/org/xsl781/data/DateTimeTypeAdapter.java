package org.xsl781.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;



public class DateTimeTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(src.getTime());
	}

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}
		return new Date(json.getAsLong());
	}

}