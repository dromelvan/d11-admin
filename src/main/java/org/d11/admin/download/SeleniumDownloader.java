package org.d11.admin.download;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class SeleniumDownloader extends D11Downloader {

	private WebDriver webDriver;
	private final static Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);

	public void open() {
	    if(this.webDriver == null) {
	        try {
    	        FirefoxProfile firefoxProfile = new FirefoxProfile();
    	        // Ublock Origin == good.
    	        File file = new File("lib/uBlock0@raymondhill.net.xpi");
    	        if(!file.exists()) {
    	            file = new File("src/main/resources/uBlock0@raymondhill.net.xpi");
    	        }
    	        firefoxProfile.addExtension(file);
    	        this.webDriver = new FirefoxDriver(firefoxProfile);
    	        this.webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	        } catch(Exception e) {
	            logger.error("Error when opening WebDriver:", e);
	        }
	    }
	}

	public void close() {
	    if(this.webDriver != null) {
	        this.webDriver.close();
	    }
	}

	@Override
	public File download() {
		String formattedUrl = formatUrl(getUrl());

		try {
			logger.info("Downloading URL {}.", formattedUrl);

			open();

			try {
    			webDriver.get(formattedUrl);

                if (getFileName() == null) {
                    String title = webDriver.getTitle();
                    setFileName(title.replace("/", "_") + ".html");
                }

                setFile(new File(getDirectory(), formatFileName(getFileName())));
                Files.write(webDriver.getPageSource(), getFile(), StandardCharsets.UTF_8);
			} catch(TimeoutException e) {
			    logger.error("Timeout when downloading URL {}.", formattedUrl);
			}

			return getFile();
		} catch (Exception e) {
			logger.error("Error when dowloading URL {}.", formattedUrl);
			logger.error("Error details:", e);
		}
		return null;
	}

}
