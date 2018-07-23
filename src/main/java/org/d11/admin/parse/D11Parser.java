package org.d11.admin.parse;

import java.io.File;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.D11FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class D11Parser<T extends Object> extends D11FileHandler {

	private final static Logger logger = LoggerFactory.getLogger(D11Parser.class);

	public T parse(File file) {
		logger.info("Parsing file {}.", file);
		try {
			setFile(file);
			return doParse();
		} catch (Exception e) {
			logger.error("Error when parsing file {}.", getFile());
			logger.error("Error details:", e);
		}
		return null;
	}

	protected abstract T doParse();

	@Override
	protected String getBaseDirectoryPropertyName() {
		return D11AdminProperties.BASE_DOWNLOAD_DIRECTORY;
	}

}
