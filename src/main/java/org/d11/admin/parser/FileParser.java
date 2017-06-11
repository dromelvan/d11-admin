package org.d11.admin.parser;

import java.io.File;

public interface FileParser<T extends ParserObject> extends Parser<T> {

	public void setFile(File file);

}
