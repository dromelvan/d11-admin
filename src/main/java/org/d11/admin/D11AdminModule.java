package org.d11.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.inject.AbstractModule;

public class D11AdminModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WebDriver.class).to(FirefoxDriver.class);
	}

}
