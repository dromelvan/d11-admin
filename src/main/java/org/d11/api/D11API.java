package org.d11.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.Player;
import org.d11.admin.model.Season;
import org.d11.admin.model.Team;
import org.d11.admin.model.TeamSquad;
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
			if (loginRequest.hasError()) {
				logger.error("Login failed for email {}", email);
				return false;
			} else {
				logger.info("Login successful.");
				authenticationParameters = new AuthenticationParameters(email, loginRequest.getAuthenticationToken());
				return true;
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in login request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing login request.", e);
		}
		return false;
	}

	public boolean isLoggedIn() {
		return this.authenticationParameters != null;
	}

	public List<Season> getSeasons() {
		try {
			SeasonsRequest seasonsRequest = new SeasonsRequest();
			seasonsRequest.execute();
			if (seasonsRequest.hasError()) {
				logger.error("Could not fetch seasons.");
				return null;
			} else {
				return seasonsRequest.getSeasons();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in season request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing season request.", e);
		}
		return null;
	}

	public Season getCurrentSeason() {
		try {
			CurrentSeasonRequest currentSeasonRequest = new CurrentSeasonRequest();
			currentSeasonRequest.execute();
			if (currentSeasonRequest.hasError()) {
				logger.error("Could not fetch current season.");
				return null;
			} else {
				return currentSeasonRequest.getSeason();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in season request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing season request.", e);
		}
		return null;
	}

	public Season getSeason(String name) {
		for (Season season : getSeasons()) {
			if (season.getName().equals(name)) {
				return season;
			}
		}
		return null;
	}

	public MatchDay getMatchDay(MatchDayRequest matchDayRequest) {
		try {
			matchDayRequest.execute();
			if (matchDayRequest.hasError()) {
				logger.error("Could not get match day data.");
				return null;
			} else {
				return matchDayRequest.getMatchDay();
			}
		} catch (IOException e) {
			logger.error("IOException when executing match day request.", e);
		}
		return null;
	}

	public MatchDay getCurrentMatchDay() {
		try {
			return getMatchDay(new CurrentMatchDayRequest());
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match day request:", e);
			return null;
		}
	}

	public MatchDay getUpcomingMatchDay() {
		try {
			return getMatchDay(new UpcomingMatchDayRequest());
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match day request:", e);
			return null;
		}
	}

	public List<MatchDay> getMatchDaysBySeason(String name) {
		Season season = getSeason(name);
		try {
			MatchDaysBySeasonRequest matchDaysBySeasonRequest = new MatchDaysBySeasonRequest(season.getId());
			matchDaysBySeasonRequest.execute();
			if (matchDaysBySeasonRequest.hasError()) {
				logger.error("Could not fetch match days for season {}.", name);
				return null;
			} else {
				return matchDaysBySeasonRequest.getMatchDays();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing match request.", e);
		}
		return null;
	}

	public MatchDay getMatchDayBySeasonAndMatchDayNumber(String name, int matchDayNumber) {
		for (MatchDay matchDay : getMatchDaysBySeason(name)) {
			if (matchDay.getMatchDayNumber() == matchDayNumber) {
				return matchDay;
			}
		}
		return null;
	}

	public Match getMatch(int matchId) {
		try {
			MatchRequest matchRequest = new MatchRequest(matchId);
			matchRequest.execute();
			if (matchRequest.hasError()) {
				logger.error("Could not fetch match {}.", matchId);
				return null;
			} else {
				return matchRequest.getMatch();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing match request.", e);
		}
		return null;
	}

	public List<Match> getMatchesByDate(LocalDate localDate) {
		try {
			MatchesByDateRequest matchesByDateRequest = new MatchesByDateRequest(localDate);
			matchesByDateRequest.execute();
			if (matchesByDateRequest.hasError()) {
				logger.error("Could not fetch matches for date {}.", localDate);
				return null;
			} else {
				return matchesByDateRequest.getMatches();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing match request.", e);
		}
		return null;
	}

	public Match updateMatchDateTime(int matchId, String dateTime) {
		try {
			UpdateMatchDateTimeRequest updateMatchDateTimeRequest = new UpdateMatchDateTimeRequest(matchId, dateTime);
			updateMatchDateTimeRequest.setAuthenticationParameters(this.authenticationParameters);
			updateMatchDateTimeRequest.execute();
			if (updateMatchDateTimeRequest.hasError()) {
				logger.error("Could not update match datetime for match {}", matchId);
				return null;
			} else {
				return updateMatchDateTimeRequest.getMatch();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in update match datetime request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing update match datetime request.", e);
		}
		return null;
	}

	public Team getTeamNamed(String name) {
		try {
			TeamNamedRequest teamNamedRequest = new TeamNamedRequest(name.replace(" and ", " "));
			teamNamedRequest.execute();
			if (teamNamedRequest.hasError()) {
				logger.error("Could not fetch team {}.", name);
				return null;
			} else {
				return teamNamedRequest.getTeam();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in team request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing team request.", e);
		}
		return null;
	}

	public TeamSquad getTeamSquad(int teamId) {
		try {
			TeamSquadRequest teamSquadRequest = new TeamSquadRequest(teamId);
			teamSquadRequest.execute();
			if (teamSquadRequest.hasError()) {
				logger.error("Could not fetch team squad {}.", teamId);
				return null;
			} else {
				return teamSquadRequest.getTeamSquad();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in match request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing match request.", e);
		}
		return null;
	}

	public Player getPlayer(int playerId) {
		try {
			PlayerRequest playerRequest = new PlayerRequest(playerId);
			playerRequest.execute();
			if (playerRequest.hasError()) {
				logger.error("Could not fetch player {}.", playerId);
				return null;
			} else {
				return playerRequest.getPlayer();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in player request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing player request.", e);
		}
		return null;
	}

	public List<Player> getPlayersNamed(String name) {
		try {
			PlayersNamedRequest playersNamedRequest = new PlayersNamedRequest(name);
			playersNamedRequest.execute();
			if (playersNamedRequest.hasError()) {
				logger.error("Could not fetch players {}.", name);
				return null;
			} else {
				return playersNamedRequest.getPlayers();
			}
		} catch (MalformedURLException e) {
			logger.error("Malformed URL in players request:", e);
		} catch (IOException e) {
			logger.error("IOException when executing players request.", e);
		}
		return null;
	}

}
