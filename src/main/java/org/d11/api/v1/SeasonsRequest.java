package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.d11.admin.model.Season;

import com.google.gson.reflect.TypeToken;

public class SeasonsRequest extends D11APIRequest<Map<String, List<Season>>> {

    private final static String REQUEST_URL = "http://%s/api/v1/seasons";

    public SeasonsRequest() throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost())));
    }

    public List<Season> getSeasons() {
        return getResult().get("seasons");
    }

    @Override
    protected TypeToken getTypeToken() {
        return new SeasonsTypeToken();
    }

}
