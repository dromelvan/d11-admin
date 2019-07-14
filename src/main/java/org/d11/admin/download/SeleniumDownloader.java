package org.d11.admin.download;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.inject.Inject;

public class SeleniumDownloader extends D11Downloader {

    @Inject
    private WebDriverProvider provider;
    private WebDriver webDriver;
    private boolean autoQuit = true;
	private final static Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);
	
	public boolean isAutoQuit() {
		return autoQuit;
	}

	public void setAutoQuit(boolean autoQuit) {
		this.autoQuit = autoQuit;
	}

	@Override
	public File download() {
		String formattedUrl = formatUrl(getUrl());

		try {
			logger.debug("Downloading URL {}.", formattedUrl);

			if(this.webDriver == null) {
				this.webDriver = provider.get();
			}

			try {
    			this.webDriver.get(formattedUrl);

                if (getFileName() == null) {
                    String title = this.webDriver.getTitle();
                    setFileName(title.replace("/", "_") + ".html");
                }

                setFile(new File(getDirectory(), formatFileName(getFileName())));
                Files.write(this.webDriver.getPageSource(), getFile(), StandardCharsets.UTF_8);
			} catch(TimeoutException e) {
			    logger.error("Timeout when downloading URL {}.", formattedUrl);
			}
			if(isAutoQuit()) {
				quit();
			}
			return getFile();
		} catch (Exception e) {
			logger.error("Error when dowloading URL {}.", formattedUrl);
			logger.error("Error details:", e);
		}
		return null;
	}

	public void quit() {
		this.webDriver.quit();
		this.webDriver = null;		
	}
}
