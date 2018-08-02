package org.d11.admin.daemon;

import java.util.Date;
import java.util.Timer;

import org.d11.admin.D11AdminBaseObject;

import com.coreoz.wisp.Scheduler;
import com.google.inject.Inject;

public class D11Daemon extends D11AdminBaseObject {

    private Scheduler scheduler;

    @Inject
    public D11Daemon(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start() {

//        scheduler.schedule(
//                () -> System.out.println("Tick tock."),           // the runnable to be scheduled
//                Schedules.fixedDelaySchedule(Duration.ofSeconds(1)) // the schedule associated to the runnable
//            );
        Timer timer = new Timer();
        //timer.schedule(new Foo(), 0, 1000);
        timer.schedule(new Foo(), new Date(2018, 8, 2, 0, 41));
    }
}
