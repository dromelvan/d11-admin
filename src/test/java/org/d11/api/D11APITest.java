package org.d11.api;

import org.joda.time.LocalDate;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class D11APITest {

    public static class D11APITestModule extends JukitoModule {
        @Override
        protected void configureTest() {
        }
    }

    @Test
    public void test(D11API d11API) throws Exception {
//        System.out.println(d11API.login("bajs", "korv"));
        System.out.println(d11API.login("dromelvan@fake.email.com", "password"));
//        System.out.println(d11API.getCurrentMatchDay().getMatchDayNumber());
//        System.out.println(d11API.getUpcomingMatchDay().getMatchDayNumber());
//        System.out.println(d11API.getMatch(4752).getWhoScoredId());
//        System.out.println(d11API.updateMatchDateTime(4752, "2017-08-08 21:45").getDatetime());
        System.out.println(d11API.getMatchesByDate(LocalDate.parse("2017-05-13")));

//        String x = "{\"matches\":[{\"id\":4922,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080881,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4923,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080882,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4924,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080883,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4925,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080884,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4926,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080885,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4927,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080825,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4928,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080829,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4929,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080833,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4930,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080837,\"datetime\":\"2017-05-13T17:00:00.000Z\"},{\"id\":4931,\"created_at\":\"2016-08-03T11:55:10Z\",\"updated_at\":\"2017-01-07T10:14:57Z\",\"whoscored_id\":1080841,\"datetime\":\"2017-05-13T17:00:00.000Z\"}]}";
//        TypeToken<Map<String, Match>> map = new Gson().fromJson(x, MatchesTypeToken.class);
//        System.out.println(map);
    }

}
