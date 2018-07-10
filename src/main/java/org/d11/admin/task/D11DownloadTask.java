package org.d11.admin.task;

import java.io.IOException;
import java.net.URL;

import org.d11.admin.reader.jsoup.JSoupURLReader;
import org.jsoup.nodes.Document;

public abstract class D11DownloadTask<T extends Object> extends D11FileTask<T> {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected Document download() throws IOException {
		return download(getUrl());
	}

	protected Document download(String url) throws IOException {
		URL URL = new URL(url);
		JSoupURLReader jSoupURLReader = new JSoupURLReader(URL);
		Document document = jSoupURLReader.read();
		return document;
	}

}
