package org.d11.admin.task;

import org.d11.admin.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public abstract class D11ParseTask<T extends Object, U extends FileParser<?>> extends D11FileTask<T> {

	@Inject
	private U parser;
	private final static Logger logger = LoggerFactory.getLogger(D11ParseTask.class);

	public U getParser() {
		return parser;
	}

	public void setParser(U parser) {
		this.parser = parser;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean execute() {
		try {
			logger.info("Parsing file {}.", getSourceFile().getAbsolutePath());
			getParser().setFile(getSourceFile());
			T result = (T) getParser().parse();
			setResult(result);
			return true;
		} catch (Exception e) {
			logger.error("Failed when parsing file.", e);
		}

		return false;
	}

}
