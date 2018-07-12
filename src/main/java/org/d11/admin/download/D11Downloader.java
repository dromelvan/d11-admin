package org.d11.admin.download;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.D11FileHandler;
import org.d11.admin.reader.jsoup.JSoupURLReader;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public abstract class D11Downloader extends D11FileHandler {

	private String url;
	private String downloadDirectoryName = "";
	private String fileName;
	private final static Logger logger = LoggerFactory.getLogger(D11Downloader.class);

	public D11Downloader() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownloadDirectoryName() {
		return downloadDirectoryName;
	}

	public void setDownloadDirectoryName(String downloadDirectoryName) {
		this.downloadDirectoryName = downloadDirectoryName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File download() {
		String formattedUrl = formatUrl(getUrl());

		try {
			URL URL = new URL(formattedUrl);
			JSoupURLReader jSoupURLReader = new JSoupURLReader(URL);
			Document document = jSoupURLReader.read();

			setFile(new File(getDownloadDirectory(), formatFileName(getFileName())));
			Files.write(document.toString(), getFile(), StandardCharsets.UTF_8);

			return getFile();
		} catch (Exception e) {
			logger.error("Error when dowloading URL {}.", formattedUrl);
			logger.error("Error details:", e);
		}
		return null;
	}

	protected String formatUrl(String url) {
		return url;
	}

	protected String formatDownloadDirectoryName(String downloadDirectoryName) {
		return downloadDirectoryName;
	}

	protected String formatFileName(String fileName) {
		return fileName;
	}

	protected File getDownloadDirectory() {
		File baseFileDirectory = new File(getProperty(D11AdminProperties.BASE_DOWNLOAD_DIRECTORY));
		File fileDirectory = new File(baseFileDirectory, formatDownloadDirectoryName(getDownloadDirectoryName()));
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		return fileDirectory;
	}

}
