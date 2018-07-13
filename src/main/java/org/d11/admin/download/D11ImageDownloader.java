package org.d11.admin.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.d11.admin.D11AdminProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public abstract class D11ImageDownloader extends D11Downloader {

    private final static Logger logger = LoggerFactory.getLogger(D11Downloader.class);

    @Override
    public File download() {
        String formattedUrl = formatUrl(getUrl());

        try {
            URL url = new URL(formattedUrl);
            setFile(new File(getDirectory(), formatFileName(getFileName())));
            logger.info("Downloading image {} to file {}.", formattedUrl, getFile().getName());

            File tmpFile = new File(getProperty(D11AdminProperties.BASE_DOWNLOAD_DIRECTORY), "image.tmp");
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
            for (int bit; (bit = inputStream.read()) != -1;) {
                outputStream.write(bit);
            }
            outputStream.close();
            inputStream.close();

            if(getFile().exists() && hash(getFile()).equals(hash(tmpFile))) {
                logger.info("File {} with same hash as downloaded file already exists.", getFile().getName());
                tmpFile.delete();
            } else {
                Files.move(tmpFile, getFile());
            }

            return getFile();
        } catch (Exception e) {
            logger.error("Error when dowloading image {}.", formattedUrl);
            logger.error("Error details:", e);
        }
        return null;
    }

    @Override
    protected String getBaseDirectoryPropertyName() {
        return D11AdminProperties.BASE_DATA_DIRECTORY;
    }

}
