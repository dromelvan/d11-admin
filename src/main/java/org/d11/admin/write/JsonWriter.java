package org.d11.admin.write;

import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JsonWriter<T extends Object> extends D11Writer<T> {

	private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final static Logger logger = LoggerFactory.getLogger(JsonWriter.class);

	@Override
	public File write(T object) {
		try {
			setFile(new File(getDirectory(), formatFileName(getFileName())));
			logger.info("Writing file {}", getFile());
			FileWriter fileWriter = new FileWriter(getFile());
			fileWriter.write(gson.toJson(object));
			fileWriter.close();
			return getFile();
		} catch (Exception e) {
			logger.error("Could not write object {} to file {}.", object.getClass().getName(), getFile());
			logger.error("Error details:", e);
		}
		return null;
	}
}
