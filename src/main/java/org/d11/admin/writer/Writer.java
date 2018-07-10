package org.d11.admin.writer;

import java.util.List;

import org.d11.admin.parser.ParserObject;

public interface Writer<T extends ParserObject> {

	public void write(List<T> parserObject);

}
