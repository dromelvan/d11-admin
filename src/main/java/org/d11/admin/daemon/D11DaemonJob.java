package org.d11.admin.daemon;

import org.d11.admin.task.D11Task;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class D11DaemonJob<T extends D11Task<?>> implements Job {

	@Inject
	private Provider<T> provider;
	private final static Logger logger = LoggerFactory.getLogger(D11DaemonJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		T task = this.provider.get();
		task.execute();
	}

}
