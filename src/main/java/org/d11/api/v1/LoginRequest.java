package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class LoginRequest extends D11APIRequest<Map<String, String>> {

    private final static String REQUEST_URL = "http://%s/api/v1/users/request_authentication_token?user[email]=%s&user[password]=%s";

    public LoginRequest(String email, String password) throws MalformedURLException {
        setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), email, password)));
    }

    public String getAuthenticationToken() {
        return getResult().get("authentication_token");
    }
}
