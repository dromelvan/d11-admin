package org.d11.admin.daemon;

import org.d11.admin.D11AdminBaseObject;
import org.d11.admin.task.D11Task;
import org.d11.api.v1.D11API;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class D11DaemonJob<T extends D11Task<?>> extends D11AdminBaseObject implements Job {

	@Inject
	private Provider<T> provider;
    @Inject
    private D11API d11Api;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        if(getD11Api().login(getUser(), getPassword())) {
    		T task = this.provider.get();
    		doExecute(task);
        }
	}

	protected void doExecute(T task) {
	    task.execute();
	}

    protected D11API getD11Api() {
        return this.d11Api;
    }

}
