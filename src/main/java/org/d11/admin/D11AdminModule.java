package org.d11.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

public class D11AdminModule extends AbstractModule {

	private final static Logger logger = LoggerFactory.getLogger(D11AdminModule.class);

	@Override
	protected void configure() {
//		bind(WebDriver.class).to(FirefoxDriver.class);
//			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//			bind(Scheduler.class).in(Singleton.class);
	}

}
