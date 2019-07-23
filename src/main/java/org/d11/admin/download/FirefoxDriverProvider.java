package org.d11.admin.download;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverProvider extends WebDriverProvider {

	@Override
	public WebDriver get() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addPreference("permissions.default.image", 2);
		firefoxOptions.addPreference("permissions.default.stylesheet", 2);
		firefoxOptions.setHeadless(true);
		
		String driver = "";
		if(SystemUtils.IS_OS_MAC) {			
			driver = "geckodriver-mac";
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		} else if(SystemUtils.IS_OS_LINUX) {
			driver = "geckodriver-linux";
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		}

        File geckoDriverFile = new File("lib/geckodriver/" + driver);
        if(geckoDriverFile.exists()) {
        	System.setProperty("webdriver.gecko.driver", "lib/geckodriver/" + driver);
        } else {
        	System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver/" + driver);
        }
        
		FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
		
		firefoxDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		return firefoxDriver;		
	}
	
}
