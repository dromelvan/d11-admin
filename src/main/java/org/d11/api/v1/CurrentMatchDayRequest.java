package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;

public class CurrentMatchDayRequest extends MatchDayRequest {

    private final static String REQUEST_URL = "http://%s/api/v1/match_days/current";

    public CurrentMatchDayRequest() throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost())));
    }

}
