package org.d11.admin;

public class D11AdminBaseObject {

    private final static D11AdminProperties D11_ADMIN_PROPERTIES = new D11AdminProperties();

    protected String getProperty(String propertyName) {
        return D11AdminBaseObject.D11_ADMIN_PROPERTIES.getProperty(propertyName);
    }

}
