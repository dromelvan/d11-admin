package org.d11.admin;

import java.io.File;

public abstract class D11FileHandler extends D11AdminBaseObject {

	private String directoryName = "";
	private String fileName;
	private File file;

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	protected String formatDirectoryName(String directoryName) {
		return directoryName;
	}

	protected String formatFileName(String fileName) {
		return fileName;
	}

	protected abstract String getBaseDirectoryPropertyName();

	protected File getDirectory() {
		File baseFileDirectory = new File(getProperty(getBaseDirectoryPropertyName()));
		File fileDirectory = new File(baseFileDirectory, formatDirectoryName(getDirectoryName()));
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		return fileDirectory;
	}

}
