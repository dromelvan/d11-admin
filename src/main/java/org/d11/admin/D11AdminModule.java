package org.d11.admin;

import org.d11.admin.download.ChromeDriverProvider;
import org.d11.admin.download.WebDriverProvider;
import org.d11.api.v1.D11API;
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
		D11AdminProperties d11AdminProperties = new D11AdminProperties();
		try {
			@SuppressWarnings("unchecked")
			Class<? extends WebDriverProvider> webDriverProviderClass = (Class<? extends WebDriverProvider>)Class.forName(d11AdminProperties.getProperty(D11AdminProperties.WEBDRIVER_PROVIDER));
	        bind(WebDriverProvider.class).to(webDriverProviderClass);
		} catch(ClassNotFoundException e) {
			logger.error("Could not find webdriver provider class.", e);
		}
	    bind(D11API.class).in(Singleton.class);
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

