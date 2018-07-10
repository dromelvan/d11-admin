package org.d11.admin.task;

import java.io.File;

import org.d11.admin.D11AdminBaseObject;
import org.d11.admin.D11AdminProperties;

public abstract class D11Task<T extends Object> extends D11AdminBaseObject {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public abstract boolean execute();

	protected File getDirectory(String directory) {
		File baseFileDirectory = new File(getProperty(D11AdminProperties.BASE_FILE_DIRECTORY));
		File fileDirectory = new File(baseFileDirectory, directory);
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		return fileDirectory;
	}

}
