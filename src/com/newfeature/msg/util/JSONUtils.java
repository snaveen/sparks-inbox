package com.newfeature.msg.util;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;

public class JSONUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static final String getJSONString(Object object) {
		if (object != null) {
			try {
				return objectMapper.writeValueAsString(object);
			} catch (JsonGenerationException e) {
				Log.e("JSONUtils", "JsonGenerationException occurred ", e);
			} catch (JsonMappingException e) {
				Log.e("JSONUtils", "JsonMappingException occurred ", e);
			} catch (IOException e) {
				Log.e("JSONUtils", "IOException occurred ", e);
			}
		}
		return null;
	}

	public static final <T> T getObject(String jsonString, Class<T> className) {
		if (jsonString != null && !"".equalsIgnoreCase(jsonString)
				&& className != null) {
			try {
				return objectMapper.readValue(jsonString, className);
			} catch (JsonParseException e) {
				Log.e("JSONUtils", "JsonParseException occurred ", e);
			} catch (JsonMappingException e) {
				Log.e("JSONUtils", "JsonMappingException occurred ", e);
			} catch (IOException e) {
				Log.e("JSONUtils", "IOException occurred ", e);
			}
		}
		return null;
	}

	public static final <T> T getObject(InputStream inputStream,
			Class<T> className) {
		if (inputStream != null && className != null) {
			try {
				return objectMapper.readValue(inputStream, className);
			} catch (JsonParseException e) {
				Log.e("JSONUtils", "JsonParseException occurred ", e);
			} catch (JsonMappingException e) {
				Log.e("JSONUtils", "JsonMappingException occurred ", e);
			} catch (IOException e) {
				Log.e("JSONUtils", "IOException occurred ", e);
			}
		}
		return null;
	}

}
