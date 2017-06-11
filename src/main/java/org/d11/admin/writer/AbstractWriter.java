package org.d11.admin.writer;

import org.d11.admin.parser.ParserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWriter<T extends ParserObject> implements Writer<T> {

	private final static Logger logger = LoggerFactory.getLogger(AbstractWriter.class);

	public AbstractWriter() {
		logger.debug("Using writer {}.", getClass().getSimpleName());
	}
}
