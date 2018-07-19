package org.d11.admin.parse.jsoup;

import java.io.File;

import org.d11.admin.parse.D11Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class JSoupParser<T extends Object> extends D11Parser<T> {

	private Document document;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public T parse(File file) {
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			setDocument(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return super.parse(file);
	}

}
