package org.d11.admin.task.whoscored;

import org.d11.admin.download.SeleniumDownloader;
import org.d11.admin.task.D11Task;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class WhoScoredDownloaderTask<T extends Object, U extends SeleniumDownloader> extends D11Task<T> {

    @Inject
    private Provider<U> provider;

    protected U getDownloader() {
        return provider.get();
    }

    public void close() {
        getDownloader().close();
    }

}
