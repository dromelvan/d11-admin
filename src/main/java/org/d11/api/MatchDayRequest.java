package org.d11.api;

import java.util.Map;

import org.d11.admin.model.MatchDay;

import com.google.gson.reflect.TypeToken;

public class MatchDayRequest extends D11APIRequest<Map<String, MatchDay>> {

    private final static String REQUEST_URL = "http://%s/api/v1/match_days/%";

    public MatchDay getMatchDay() {
        return getResult().get("match_day");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new MatchDayTypeToken();
    }

}
