package org.d11.admin.download.whoscored;

import org.d11.admin.download.HtmlUnitDownloader;

public class WhoScoredMatchHtmlUnitDownloader extends HtmlUnitDownloader {

    private int whoScoredId;
    private String season;
    private int matchDay;

    public WhoScoredMatchHtmlUnitDownloader() {
        setUrl("https://www.whoscored.com/Matches/%d/Live");
        setDirectoryName("whoscored.com/matches/%s/%02d");
    }

    public int getWhoScoredId() {
        return whoScoredId;
    }

    public void setWhoScoredId(int whoScoredId) {
        this.whoScoredId = whoScoredId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
    }

    @Override
    protected String formatUrl(String url) {
        return String.format(getUrl(), getWhoScoredId());
    }

    @Override
    public String formatDirectoryName(String directoryName) {
        return String.format(directoryName, getSeason(), getMatchDay());
    }

}
