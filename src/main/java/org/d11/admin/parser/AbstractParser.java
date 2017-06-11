package org.d11.admin.parser;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractParser<T extends ParserObject> implements Parser<T> {

	private Set<T> parserObjects;
	private final static Logger logger = LoggerFactory.getLogger(AbstractParser.class);

	public AbstractParser() {
		logger.debug("Using parser {}.", getClass().getSimpleName());
	}

	public Set<T> getParserObjects() {
		return parserObjects;
	}

	public void setParserObjects(Set<T> parserObjects) {
		this.parserObjects = parserObjects;
	}

}
