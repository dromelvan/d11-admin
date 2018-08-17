package org.d11.admin;

import java.io.File;

import org.d11.admin.daemon.D11Daemon;
import org.d11.admin.task.GenerateD11FixturesTask;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class D11AdminTest {

    public static class D11APITestModule extends JukitoModule {
        @Override
        protected void configureTest() {
        }
    }

    //@Test
    public void generateD11Fixtures(GenerateD11FixturesTask task) {
        if(task.execute()) {
            File file = task.getResult();
            System.out.println(file);
        }
    }

    @Test
    public void d11Daemon(D11Daemon d11Daemon) {
        d11Daemon.setPassword("password");
        d11Daemon.start();
        while(true) {

        }
    }
}
