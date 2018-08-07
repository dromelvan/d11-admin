package org.d11.admin.daemon;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

final class GuiceJobFactory implements JobFactory {

	private final Injector guice;
	private final static Logger logger = LoggerFactory.getLogger(GuiceJobFactory.class);

	@Inject
	public GuiceJobFactory(Injector guice) {
		this.guice = guice;
	}

	@Override
	public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
		JobDetail jobDetail = triggerFiredBundle.getJobDetail();
		Class<?> jobClass = jobDetail.getJobClass();

		try {
			return (Job) guice.getInstance(jobClass);
		} catch (Exception e) {
			logger.error("Could not create new job.", e);
			throw new UnsupportedOperationException(e);
		}
	}

}
