package org.d11.admin.writer;

import java.util.Set;

import org.d11.admin.parser.ParserObject;

public interface Writer<T extends ParserObject> {

	public void write(Set<T> parserObject);

}
