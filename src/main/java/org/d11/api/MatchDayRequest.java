package org.d11.api;

import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class MatchDayRequest extends D11APIRequest<Map<String, MatchDay>> {

    public MatchDay getMatchDay() {
        return getResult().get("match_day");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new MatchDayTypeToken();
    }

}
