package org.d11.admin.task;

import java.io.File;

import org.d11.admin.D11AdminBaseObject;
import org.d11.admin.D11AdminProperties;
import org.d11.api.v1.D11API;

import com.google.inject.Inject;

public abstract class D11Task<T extends Object> extends D11AdminBaseObject {

    @Inject
    private D11API d11Api;
	private T result;

	public D11API getD11Api() {
        return d11Api;
    }

    public void setD11Api(D11API d11Api) {
        this.d11Api = d11Api;
    }

    public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public abstract boolean execute();

	protected File getDataDirectory(String directory) {
		File baseDataDirectory = new File(getProperty(D11AdminProperties.BASE_DATA_DIRECTORY));
		File dataDirectory = new File(baseDataDirectory, directory);
		if (!dataDirectory.exists()) {
			dataDirectory.mkdirs();
		}
		return dataDirectory;
	}

}
