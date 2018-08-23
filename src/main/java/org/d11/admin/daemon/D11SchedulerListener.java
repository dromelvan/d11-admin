package org.d11.admin.daemon;

import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.listeners.SchedulerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class D11SchedulerListener extends SchedulerListenerSupport {

    private final static Logger logger = LoggerFactory.getLogger(D11SchedulerListener.class);

    @Override
    public void jobScheduled(Trigger trigger) {
        if(trigger.getKey().getGroup() != null && trigger.getKey().getGroup().equals("D11")) {
            if(trigger instanceof SimpleTrigger) {
                SimpleTrigger simpleTrigger = (SimpleTrigger)trigger;
                logger.info("Scheduled simple trigger for job {}.{}. Fire time {}, repeat count {}.", simpleTrigger.getJobKey().getGroup(), simpleTrigger.getJobKey().getName(), simpleTrigger.getNextFireTime(), simpleTrigger.getRepeatCount());
            } else if(trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger)trigger;
                logger.info("Scheduled cron trigger for job {}.{}. Cron expression {}.", cronTrigger.getJobKey().getGroup(), cronTrigger.getJobKey().getName(), cronTrigger.getCronExpression());
            }
        }
    }

}
