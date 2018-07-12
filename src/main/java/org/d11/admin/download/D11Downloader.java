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
	private final static Logger logger = LoggerFactory.getLogger(D11Downloader.class);

	public D11Downloader() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File download() {
		String formattedUrl = formatUrl(getUrl());

		try {
			URL URL = new URL(formattedUrl);
			JSoupURLReader jSoupURLReader = new JSoupURLReader(URL);
			Document document = jSoupURLReader.read();

			setFile(new File(getDirectory(), formatFileName(getFileName())));
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

	@Override
	protected String getBaseDirectoryPropertyName() {
		return D11AdminProperties.BASE_DOWNLOAD_DIRECTORY;
	}

}
