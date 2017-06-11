package org.d11.admin.task;

import org.d11.admin.D11AdminBaseObject;

public abstract class D11Task<T extends Object> extends D11AdminBaseObject {

    private T result;

    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }

    public abstract boolean execute();

}
