package org.d11.admin.parser;

import java.io.IOException;
import java.util.List;

public interface Parser<T extends ParserObject> {

	public List<T> parse() throws IOException;

}
