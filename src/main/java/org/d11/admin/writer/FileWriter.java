package org.d11.admin.writer;

import java.io.File;

import org.d11.admin.parser.ParserObject;

public interface FileWriter<T extends ParserObject> extends Writer<T> {

	public void setFile(File file);

}
