package org.d11.admin;

import org.d11.api.v1.D11API;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class D11AdminModule extends AbstractModule {

	@Override
	protected void configure() {
	    bind(D11API.class).in(Singleton.class);
	}

}
