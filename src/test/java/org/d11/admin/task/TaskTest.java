package org.d11.admin.task;

import java.util.List;

import org.d11.api.Match;
import org.joda.time.LocalDate;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class TaskTest {

    public static class D11APITestModule extends JukitoModule {
        @Override
        protected void configureTest() {
        }
    }

    @Test
    public void getMatchesByDate(GetMatchesByDateTask getMatchesByDateTask) throws Exception {
        getMatchesByDateTask.setLocalDate(LocalDate.parse("2017-05-13"));
        if(getMatchesByDateTask.execute()) {
            List<Match> matches = getMatchesByDateTask.getResult();
            for(Match match : matches) {
                System.out.println(match);
            }
        }
    }

}
