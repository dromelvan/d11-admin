package org.d11.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.d11.admin.D11AdminBaseObject;
import org.d11.admin.D11AdminProperties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class D11APIRequest<T extends Object> extends D11AdminBaseObject {

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE;
    }

    private URL url = null;
    private RequestMethod requestMethod = RequestMethod.GET;
    private D11APIError error = null;
    private T result = null;
    private Map<String, String> requestParameters = new HashMap<String, String>();

    protected void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.requestParameters.putAll(authenticationParameters);
    }

    protected void setRequestParameter(String key, String value) {
        this.requestParameters.put(key, value);
    }

    public void execute() throws IOException {
        switch(getRequestMethod()) {
            case GET:
                get();
              break;
            case PUT:
                put();
              break;
            default: throw new RuntimeException("Request method " + getRequestMethod() + " not implemented.");
        }
    }

    private void get() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)getUrl().openConnection();
        httpURLConnection.setRequestMethod(getRequestMethod().toString());
        httpURLConnection.connect();

        handleResponse(httpURLConnection);
    }

    private void put() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: this.requestParameters.keySet()) {
            if (stringBuilder.length() != 0) stringBuilder.append('&');
            stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
            stringBuilder.append('=');
            stringBuilder.append(URLEncoder.encode(this.requestParameters.get(key), "UTF-8"));
        }
        byte[] requestDataBytes = stringBuilder.toString().getBytes("UTF-8");

        HttpURLConnection httpURLConnection = (HttpURLConnection)getUrl().openConnection();
        httpURLConnection.setRequestMethod(getRequestMethod().toString());
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(requestDataBytes.length));
        httpURLConnection.setDoOutput(true);
        httpURLConnection.getOutputStream().write(requestDataBytes);

        handleResponse(httpURLConnection);
    }

    private void handleResponse(HttpURLConnection httpURLConnection) throws IOException {
        if(httpURLConnection.getResponseCode() >= 400) {
            InputStream inputStream = httpURLConnection.getErrorStream();
            Map<String, D11APIError> d11apiErrorMap = new Gson().fromJson(new InputStreamReader(inputStream, "UTF-8"), new TypeToken<Map<String, D11APIError>>(){}.getType());
            setError(d11apiErrorMap.get("error"));
        } else {
            InputStream inputStream = httpURLConnection.getInputStream();
            T result = new Gson().fromJson(new InputStreamReader(inputStream, "UTF-8"), getTypeToken().getType());
            setResult(result);
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public D11APIError getError() {
        return error;
    }

    public void setError(D11APIError error) {
        this.error = error;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    protected TypeToken getTypeToken() {
        return new TypeToken<Map<String, String>>(){};
    }

    protected String getAPIHost() {
        return getProperty(D11AdminProperties.API_HOST);
    }

    protected Map<String, String> getRequestParameters() {
        return new HashMap<String, String>();
    }
}
