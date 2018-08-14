package org.d11.api.v1;

import java.util.HashMap;

public class AuthenticationParameters extends HashMap<String, String> {

    public final static String EMAIL_KEY = "user[email]";
    public final static String AUTHENTICATION_TOKEN_KEY = "user[authentication_token]";
    private static final long serialVersionUID = 2266485081471607141L;

    public AuthenticationParameters(String email, String authenticationToken) {
        put(EMAIL_KEY, email);
        put(AUTHENTICATION_TOKEN_KEY, authenticationToken);
    }

    public String getEmail() {
        return get(EMAIL_KEY);
    }

    public String getAuthenticationToken() {
        return get(AUTHENTICATION_TOKEN_KEY);
    }
}
