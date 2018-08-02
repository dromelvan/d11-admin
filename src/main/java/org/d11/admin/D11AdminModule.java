package org.d11.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

public class D11AdminModule extends AbstractModule {

	private final static Logger logger = LoggerFactory.getLogger(D11AdminModule.class);

	@Override
	protected void configure() {
		bind(WebDriver.class).to(FirefoxDriver.class);
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			bind(Scheduler.class).toInstance(scheduler);
		} catch (SchedulerException e) {
			logger.error("Could not get default scheduler.", e);
		}
	}

}
