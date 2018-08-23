package org.d11.admin.daemon;

import org.d11.admin.D11AdminBaseObject;
import org.d11.admin.D11AdminProperties;
import org.d11.admin.task.D11Task;
import org.d11.api.v1.D11API;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class D11DaemonJob<T extends D11Task<?>> extends D11AdminBaseObject implements Job {

	@Inject
	private Provider<T> provider;
    @Inject
    private D11API d11Api;
    private JobExecutionContext jobExecutionContext;
    private final static Logger logger = LoggerFactory.getLogger(D11DaemonJob.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    this.jobExecutionContext = jobExecutionContext;
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

    protected JobExecutionContext getJobExecutionContext() {
        return this.jobExecutionContext;
    }

    protected void reschedule(String name) {
        reschedule(name, getNow());
    }

    protected void reschedule(String name, LocalDateTime fromLocalDateTime) {
        JobKey jobKey = this.jobExecutionContext.getJobDetail().getKey();
        TriggerKey triggerKey = this.jobExecutionContext.getTrigger().getKey();
        fromLocalDateTime = fromLocalDateTime.withFieldAdded(DurationFieldType.minutes(), getRefreshRate());

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, triggerKey.getGroup()).forJob(jobKey.getName(), jobKey.getGroup()).startAt(fromLocalDateTime.toDate()).build();

        try {
            Scheduler scheduler = this.jobExecutionContext.getScheduler();
            if(scheduler.getTrigger(trigger.getKey()) != null) {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        } catch(SchedulerException e) {
            logger.error("Could not schedule new update.", e);
        }
    }

    protected int getRefreshRate() {
        try {
            return Integer.parseInt(getProperty(D11AdminProperties.DAEMON_UPDATE_REFRESH_RATE));
        } catch(NumberFormatException e) {
            return 0;
        }
    }

}
