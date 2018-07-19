package org.d11.admin.download.whoscored;

import org.d11.admin.download.D11Downloader;

public class WhoScoredPlayerDownloader extends D11Downloader {

	private int id;
	private String name;

	public WhoScoredPlayerDownloader() {
		setUrl("https://www.whoscored.com/Players/%d");
		setDirectoryName("whoscored.com/players");
		setFileName("%s (%s).html");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected String formatUrl(String url) {
		return String.format(getUrl(), id);
	}

	@Override
	protected String formatFileName(String fileName) {
		return String.format(fileName, getName(), getId());
	}

}
