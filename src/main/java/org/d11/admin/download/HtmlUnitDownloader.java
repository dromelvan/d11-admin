package org.d11.admin.download;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.io.Files;
import com.google.inject.Inject;

public class HtmlUnitDownloader extends D11Downloader {

    @Inject
    private WebClientProvider provider;	
	private final static Logger logger = LoggerFactory.getLogger(HtmlUnitDownloader.class);
	
	@Override
	public File download() {
		String formattedUrl = formatUrl(getUrl());
		
		try {
			logger.debug("Downloading URL {}.", formattedUrl);
		
			WebClient webClient = this.provider.get();
			
			HtmlPage htmlPage = webClient.getPage(formattedUrl);
			String contentAsStringl = htmlPage.getWebResponse().getContentAsString();

            if (getFileName() == null) {
                String title = htmlPage.getTitleText();
                setFileName(title.replace("/", "_") + ".html");
            }
			
			setFile(new File(getDirectory(), formatFileName(getFileName())));			
			Files.write(contentAsStringl, getFile(), StandardCharsets.UTF_8);
			webClient.close();		
			return getFile();
		} catch (Exception e) {
			logger.error("Error when dowloading URL {}.", formattedUrl);
			logger.error("Error details:", e);
		}
		return null;
	}
}
