package org.d11.admin.daemon;

import javax.inject.Provider;

import org.d11.admin.D11AdminBaseObject;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class D11Daemon extends D11AdminBaseObject {

    @Inject
    private Provider<Scheduler> provider;
	private GuiceJobFactory guiceJobFactory;
	private final static Logger logger = LoggerFactory.getLogger(D11Daemon.class);

	@Inject
	public D11Daemon(GuiceJobFactory guiceJobFactory) {
	    this.guiceJobFactory = guiceJobFactory;
	}

	public void start() {
	    try {
	        Scheduler scheduler = this.provider.get();
	        try {
	            scheduler.setJobFactory(guiceJobFactory);
	        } catch (SchedulerException e) {
	            logger.error("Could not set job factory.", e);
	        }

	        scheduler.getListenerManager().addSchedulerListener(new D11SchedulerListener());
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error("Could not start scheduler.", e);
		}
	}

}
