package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;

public class UpcomingMatchDayRequest extends MatchDayRequest {

    private final static String REQUEST_URL = "http://%s/api/v1/match_days/upcoming";

    public UpcomingMatchDayRequest() throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost())));
    }

}
