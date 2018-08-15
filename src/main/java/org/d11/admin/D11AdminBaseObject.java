package org.d11.admin;

public class D11AdminBaseObject {

    private final static D11AdminProperties D11_ADMIN_PROPERTIES = new D11AdminProperties();

    protected String getProperty(String propertyName) {
        return D11AdminBaseObject.D11_ADMIN_PROPERTIES.getProperty(propertyName);
    }

    protected void setProperty(String propertyName, String value) {
        D11AdminBaseObject.D11_ADMIN_PROPERTIES.setProperty(propertyName, value);
    }

    protected String getUser() {
        return getProperty(D11AdminProperties.API_USER);
    }

    protected void setUser(String user) {
        setProperty(D11AdminProperties.API_USER, user);
    }

    protected String getPassword() {
        return getProperty(D11AdminProperties.API_PASSWORD);
    }

    protected void setPassword(String password) {
        setProperty(D11AdminProperties.API_PASSWORD, password);
    }

}
