package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.Season;

import com.google.gson.reflect.TypeToken;

public class SeasonRequest extends D11APIRequest<Map<String, Season>> {

    private final static String REQUEST_URL = "http://%s/api/v1/seasons/%s";

    public SeasonRequest(int seasonId) throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), seasonId)));
    }

    public Season getSeason() {
        return getResult().get("season");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new SeasonTypeToken();
    }

}
