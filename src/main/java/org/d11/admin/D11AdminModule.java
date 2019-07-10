package org.d11.admin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.d11.api.v1.D11API;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class D11AdminModule extends AbstractModule {

    private final static Logger logger = LoggerFactory.getLogger(D11AdminModule.class);

	@Override
	protected void configure() {
	    bind(D11API.class).in(Singleton.class);
	}

	@Provides
	public WebDriver provideWebDriver() {
        try {
        	WebDriver webDriver = provideChromeDriver();
            return webDriver;
        } catch(Exception e) {
            logger.error("Error when creating WebDriver:", e);
        }
        return null;
	}

	private WebDriver provideChromeDriver() {
		String driver = "";
		if(SystemUtils.IS_OS_MAC) {			
			driver = "chromedriver-mac";
		} else if(SystemUtils.IS_OS_LINUX) {
			driver = "chromedriver-linux";
		} else if(SystemUtils.IS_OS_WINDOWS) {
			driver = "chromedriver-win.exe";
		}

        File chromeDriverFile = new File("lib/chromedriver/" + driver);
        File uBlockFile = new File("lib/uBlock0@raymondhill.net.crx");
        if(chromeDriverFile.exists()) {
        	System.setProperty("webdriver.chrome.driver", "lib/chromedriver/" + driver);
        } else {
        	System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver/" + driver);
        	uBlockFile = new File("src/main/resources/uBlock0@raymondhill.net.crx");
        }

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(uBlockFile);            
		ChromeDriver webDriver = new ChromeDriver(chromeOptions);

		webDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		return webDriver;
	}
		
	@Provides
	@Singleton
	public Scheduler provideScheduler() {
	    try {
	        return StdSchedulerFactory.getDefaultScheduler();
	    } catch(SchedulerException e) {
	        logger.error("Could not bind Quartz scheduler.", e);
	        return null;
	    }
	}
}

