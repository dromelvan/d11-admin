package org.d11.admin.download;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverProvider extends WebDriverProvider {

	@Override
	public WebDriver get() {
        ChromeOptions chromeOptions = new ChromeOptions();            
        
        HashMap<String, Object> images = new HashMap<String, Object>();
        images.put("images", 2);
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values", images);
        chromeOptions.setExperimentalOption("prefs", prefs);
        
        chromeOptions.addArguments("user-data-dir=.chrome");
        
		String driver = "";
		if(SystemUtils.IS_OS_MAC) {			
			driver = "chromedriver-mac";
			//chromeOptions.setHeadless(true);
		} else if(SystemUtils.IS_OS_LINUX) {
			driver = "chromedriver-linux";
		} else if(SystemUtils.IS_OS_WINDOWS) {
			driver = "chromedriver-win.exe";
			chromeOptions.setHeadless(true);
			chromeOptions.addArguments("disable-gpu");
		}

        File chromeDriverFile = new File("lib/chromedriver/" + driver);
        File uBlockFile = new File("lib/uBlock0@raymondhill.net.crx");
        if(chromeDriverFile.exists()) {
        	System.setProperty("webdriver.chrome.driver", "lib/chromedriver/" + driver);
        } else {
        	System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver/" + driver);
        	uBlockFile = new File("src/main/resources/uBlock0@raymondhill.net.crx");
        }
        chromeOptions.addExtensions(uBlockFile);
        
        System.setProperty("webdriver.chrome.silentOutput", "true");            
        
		ChromeDriver webDriver = new ChromeDriver(chromeOptions);

		webDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		return webDriver;
	}

}
