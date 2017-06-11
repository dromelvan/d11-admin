package org.d11.admin.parser;

import java.io.IOException;
import java.util.Set;

public interface Parser<T extends ParserObject> {

	public Set<T> parse() throws IOException;

}
