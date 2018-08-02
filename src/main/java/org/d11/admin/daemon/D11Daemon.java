package org.d11.admin.daemon;

import org.d11.admin.D11AdminBaseObject;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class D11Daemon extends D11AdminBaseObject {

	private Scheduler scheduler;
	private final static Logger logger = LoggerFactory.getLogger(D11Daemon.class);

	@Inject
	public D11Daemon(Scheduler scheduler, GuiceJobFactory guiceJobFactory) {
		this.scheduler = scheduler;
		try {
			scheduler.setJobFactory(guiceJobFactory);
		} catch (SchedulerException e) {
			logger.error("Could not set job factory.", e);
		}
	}

	public void start() {
		try {
			this.scheduler.start();

			// // define the job and tie it to our MyJob class
			// JobDetail job = newJob(Foo.class)
			// .withIdentity("job1", "group1")
			// .build();
			//
			// // Trigger the job to run now, and then repeat every 40 seconds
			// Trigger trigger = newTrigger()
			// .withIdentity("trigger1", "group1")
			// .startNow()
			// .withSchedule(simpleSchedule()
			// .withIntervalInSeconds(1)
			// .repeatForever())
			// .build();
			//
			// // Tell quartz to schedule the job using our trigger
			// scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("Could not start scheduler.", e);
		}
	}

}
