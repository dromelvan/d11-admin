package org.d11.admin.parser.jsoup;

import java.io.File;

import org.d11.admin.parser.FileParser;
import org.d11.admin.parser.ParserObject;
import org.d11.admin.parser.javascript.JavaScriptVariables;
import org.d11.admin.reader.jsoup.JSoupFileReader;

public abstract class JSoupFileParser<T extends ParserObject, U extends JavaScriptVariables> extends JSoupDocumentParser<T, U> implements FileParser<T> {

	@Override
	public void setFile(File file) {
		JSoupFileReader jSoupFileReader = new JSoupFileReader(file);
		try {
			setDocument(jSoupFileReader.read());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
