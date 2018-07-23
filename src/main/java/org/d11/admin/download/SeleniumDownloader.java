package org.d11.admin.download;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SeleniumDownloader extends D11Downloader {

	@Inject
	private Provider<WebDriver> webDriverProvider;
	private final static Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);

	@Override
	public File download() {
		String formattedUrl = formatUrl(getUrl());

		try {
			logger.info("Downloading URL {}.", formattedUrl);

			WebDriver webDriver = webDriverProvider.get();
			webDriver.get(formattedUrl);

			if (getFileName() == null) {
				String title = webDriver.getTitle();
				setFileName(title.replace("/", "_") + ".html");
			}

			setFile(new File(getDirectory(), formatFileName(getFileName())));
			Files.write(webDriver.getPageSource(), getFile(), StandardCharsets.UTF_8);

			webDriver.close();

			return getFile();
		} catch (Exception e) {
			logger.error("Error when dowloading URL {}.", formattedUrl);
			logger.error("Error details:", e);
		}
		return null;
	}

}
