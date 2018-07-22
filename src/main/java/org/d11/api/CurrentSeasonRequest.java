package org.d11.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.Season;

import com.google.gson.reflect.TypeToken;

public class CurrentSeasonRequest extends D11APIRequest<Map<String, Season>> {

    private final static String REQUEST_URL = "http://%s/api/v1/seasons/current";

    public CurrentSeasonRequest() throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost())));
    }

    public Season getSeason() {
        return getResult().get("season");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new SeasonTypeToken();
    }

}
