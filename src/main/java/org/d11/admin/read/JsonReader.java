package org.d11.admin.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.d11.admin.write.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public abstract class JsonReader<T extends Object> extends D11Reader<T> {

	private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final static Logger logger = LoggerFactory.getLogger(JsonWriter.class);

	public T read(File file) {
		try {
			setFile(file);
			T result = gson.fromJson(new FileReader(getFile()), getTypeToken().getType());
			return result;
		} catch (FileNotFoundException e) {
			logger.error("Could not read from file {}.", getFile());
			logger.error("Error details:", e);
		}
		return null;
	}

	protected abstract TypeToken<T> getTypeToken();

}
