package org.d11.admin.write;

import java.io.File;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.D11FileHandler;

public abstract class D11Writer<T extends Object> extends D11FileHandler {

	public abstract File write(T object);

	@Override
	protected String getBaseDirectoryPropertyName() {
		return D11AdminProperties.BASE_DATA_DIRECTORY;
	}

}
