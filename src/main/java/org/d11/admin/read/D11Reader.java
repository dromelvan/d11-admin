package org.d11.admin.read;

import java.io.File;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.D11FileHandler;

public abstract class D11Reader<T extends Object> extends D11FileHandler {

	public abstract T read(File file);

	@Override
	protected String getBaseDirectoryPropertyName() {
		return D11AdminProperties.BASE_DATA_DIRECTORY;
	}

}
