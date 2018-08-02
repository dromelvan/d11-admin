package org.d11.admin.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldTask extends D11Task<String> {

	private final static Logger logger = LoggerFactory.getLogger(HelloWorldTask.class);

	@Override
	public boolean execute() {
		String helloWorld = "Hello World!";
		logger.info(helloWorld);
		setResult(helloWorld);
		return true;
	}

}
