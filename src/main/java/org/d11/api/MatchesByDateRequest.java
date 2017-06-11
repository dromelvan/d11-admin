package org.d11.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.google.gson.reflect.TypeToken;

public class MatchesByDateRequest extends D11APIRequest<Map<String, List<Match>>> {

    private final static String REQUEST_URL = "http://%s/api/v1/matches/by_date/%s";

    public MatchesByDateRequest(LocalDate localDate) throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), localDate.toString())));
    }

    public List<Match> getMatches() {
        return getResult().get("matches");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new MatchesTypeToken();
    }

}
