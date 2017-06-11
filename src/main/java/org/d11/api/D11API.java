package org.d11.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class D11API {

    private AuthenticationParameters authenticationParameters = null;
    private final static Logger logger = LoggerFactory.getLogger(D11API.class);

    public boolean login(String email, String password) {
        try {
            LoginRequest loginRequest = new LoginRequest(email, password);
            loginRequest.execute();
            if(loginRequest.hasError()) {
                logger.error("Login failed for email {}", email);
                return false;
            } else {
                logger.info("Login successful.");
                authenticationParameters = new AuthenticationParameters(email, loginRequest.getAuthenticationToken());
                return true;
            }
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in login request:" , e);
        } catch(IOException e) {
            logger.error("IOException when executing login request.", e);
        }
        return false;
    }

    public boolean isLoggedIn() {
        return this.authenticationParameters != null;
    }

    public MatchDay getMatchDay(MatchDayRequest matchDayRequest) {
        try {
            matchDayRequest.execute();
            if(matchDayRequest.hasError()) {
                logger.error("Could not get match day data.");
                return null;
            } else {
                return matchDayRequest.getMatchDay();
            }
        } catch(IOException e) {
            logger.error("IOException when executing match day request.", e);
        }
        return null;
    }

    public MatchDay getCurrentMatchDay() {
        try {
            return getMatchDay(new CurrentMatchDayRequest());
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in match day request:" , e);
            return null;
        }
    }

    public MatchDay getUpcomingMatchDay() {
        try {
            return getMatchDay(new UpcomingMatchDayRequest());
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in match day request:" , e);
            return null;
        }
    }

    public Match getMatch(int matchId) {
        try {
            MatchRequest matchRequest = new MatchRequest(matchId);
            matchRequest.execute();
            if(matchRequest.hasError()) {
                logger.error("Could not fetch match {}.", matchId);
                return null;
            } else {
                return matchRequest.getMatch();
            }
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in match request:" , e);
        } catch(IOException e) {
            logger.error("IOException when executing match request.", e);
        }
        return null;
    }

    public List<Match> getMatchesByDate(LocalDate localDate) {
        try {
            MatchesByDateRequest matchesByDateRequest = new MatchesByDateRequest(localDate);
            matchesByDateRequest.execute();
            if(matchesByDateRequest.hasError()) {
                logger.error("Could not fetch matches for date {}.", localDate);
                return null;
            } else {
                return matchesByDateRequest.getMatches();
            }
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in match request:" , e);
        } catch(IOException e) {
            logger.error("IOException when executing match request.", e);
        }
        return null;
    }

    public Match updateMatchDateTime(int matchId, String dateTime) {
        try {
            UpdateMatchDateTimeRequest updateMatchDateTimeRequest = new UpdateMatchDateTimeRequest(matchId, dateTime);
            updateMatchDateTimeRequest.setAuthenticationParameters(this.authenticationParameters);
            updateMatchDateTimeRequest.execute();
            if(updateMatchDateTimeRequest.hasError()) {
                logger.error("Could not update match datetime for match {}", matchId);
                return null;
            } else {
                return updateMatchDateTimeRequest.getMatch();
            }
        } catch(MalformedURLException e) {
            logger.error("Malformed URL in update match datetime request:" , e);
        } catch(IOException e) {
            logger.error("IOException when executing update match datetime request.", e);
        }
        return null;
    }

}
