package org.d11.admin.parse;

import java.io.File;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.D11FileHandler;

public abstract class D11Parser<T extends Object> extends D11FileHandler {

	public T parse(File file) {
		setFile(file);
		return doParse();
	}

	protected abstract T doParse();

	@Override
	protected String getBaseDirectoryPropertyName() {
		return D11AdminProperties.BASE_DOWNLOAD_DIRECTORY;
	}

}
