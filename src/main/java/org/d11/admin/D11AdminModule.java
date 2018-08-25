package org.d11.admin;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.d11.api.v1.D11API;
import org.openqa.selenium.WebDriver;
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
	@Singleton
	public WebDriver provideWebDriver() {
        try {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            // Ublock Origin == good.
            File file = new File("lib/uBlock0@raymondhill.net.xpi");
            if(!file.exists()) {
                file = new File("src/main/resources/uBlock0@raymondhill.net.xpi");
            }
            firefoxProfile.addExtension(file);
            WebDriver webDriver = new FirefoxDriver(firefoxProfile);
            webDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
            return webDriver;
        } catch(Exception e) {
            logger.error("Error when creating WebDriver:", e);
        }
        return null;
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

