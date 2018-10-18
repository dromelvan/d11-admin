package org.d11.admin;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.d11.admin.daemon.D11Daemon;
import org.d11.admin.model.MatchDay;
import org.d11.admin.task.ActivateMatchDayTask;
import org.d11.admin.task.GenerateD11FixturesTask;
import org.d11.api.v1.D11API;
import org.joda.time.LocalDateTime;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.google.inject.Provides;
import com.google.inject.Singleton;

@RunWith(JukitoRunner.class)
public class D11AdminTest {

    public static class D11APITestModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(D11API.class).in(Singleton.class);
            try {
                bind(Scheduler.class).toInstance(StdSchedulerFactory.getDefaultScheduler());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        @Provides
        @Singleton
        public WebDriver provideWebDriver() {
            try {
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                // Ublock Origin == good.
                File file = new File("lib/uBlock0@raymondhill.net.xpi");
                if(!file.exists()) {
                    file = new File("src/main/resources/uBlock0@raymondhill.net.xpi");
                }
                firefoxProfile.addExtension(file);
                WebDriver webDriver = new FirefoxDriver(firefoxProfile);
                webDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
                return webDriver;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //@Test
    public void generateD11Fixtures(GenerateD11FixturesTask task) {
        if(task.execute()) {
            File file = task.getResult();
            System.out.println(file);
        }
    }

    //@Test
    public void activateMatchDay(D11API d11Api, ActivateMatchDayTask task) {
        if(d11Api.login("dromelvan@fake.email.com", "password")) {
            MatchDay matchDay = d11Api.getMatchDayBySeasonAndMatchDayNumber("2016-2017", 37);
            task.setMatchDay(matchDay);
            if(task.execute()) {
                System.out.println(task.getResult());
            }
        }
    }

    //@Test
    public void d11Daemon(D11Daemon d11Daemon) {
        d11Daemon.start();
        while(true) {

        }
    }

    //@Test
    public void date() {
        String a = "2017-05-21T17:00:00.000Z";
        String b = "2017-05-21 17:00";

        LocalDateTime aa = LocalDateTime.parse(a.replace("Z", ""));
        LocalDateTime bb = LocalDateTime.parse(b.replace(" ", "T"));

        System.out.println(aa + " " + bb + " " + aa.equals(bb));

    }
}
