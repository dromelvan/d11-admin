package org.d11.admin.reader.jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.io.Files;

public class SeleniumURLReader extends JSoupURLReader {

	private static WebDriver webDriver = null;

	public SeleniumURLReader() {
	    super(null);
	}

	public SeleniumURLReader(URL url) {
		super(url);
	}

	public static void openWebDriver() {
		if (webDriver == null) {
			webDriver = new FirefoxDriver();
		}
	}

	public static void closeWebDriver() {
		if (webDriver != null) {
			webDriver.close();
			webDriver = null;
		}
	}

	@Override
	public Document read() throws IOException {
		webDriver.get(getUrl().toString());
		File tmpFile = new File(System.currentTimeMillis() + ".html");

		Files.write(webDriver.getPageSource(), tmpFile, StandardCharsets.UTF_8);
		Document document = Jsoup.parse(tmpFile, "UTF-8");
		tmpFile.delete();

		return document;
	}

}
