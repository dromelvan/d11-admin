package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.MatchDay;

import com.google.gson.reflect.TypeToken;

public class FinishMatchDayRequest extends D11APIRequest<Map<String, MatchDay>> {

    private final static String REQUEST_URL = "http://%s/api/v1/match_days/%s/finish";

    public FinishMatchDayRequest(int matchDayId) throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), matchDayId)));
        setRequestMethod(RequestMethod.PUT);
    }

    public MatchDay getMatchDay() {
        return getResult().get("match_day");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new MatchDayTypeToken();
    }

}
