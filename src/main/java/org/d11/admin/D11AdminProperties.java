package org.d11.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class D11AdminProperties extends Properties {

    private final static Logger logger = LoggerFactory.getLogger(D11AdminProperties.class);
    private static final long serialVersionUID = 4797743920443954144L;

    public final static String BASE_DATA_DIRECTORY = "base.data.directory";
    public final static String BASE_DOWNLOAD_DIRECTORY = "base.download.directory";
    public final static String API_HOST = "api.host";

    public D11AdminProperties() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("org/d11/admin/d11-admin.properties");
            load(new InputStreamReader(input, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            logger.error("Could not load {}.", "org/d11/admin/d11-admin.properties");
        }
    }
}
