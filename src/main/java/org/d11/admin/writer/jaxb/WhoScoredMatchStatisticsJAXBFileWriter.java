package org.d11.admin.writer.jaxb;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.d11.admin.jaxb.match.Card;
import org.d11.admin.jaxb.match.CardType;
import org.d11.admin.jaxb.match.Cards;
import org.d11.admin.jaxb.match.Goal;
import org.d11.admin.jaxb.match.Goals;
import org.d11.admin.jaxb.match.Match;
import org.d11.admin.jaxb.match.ObjectFactory;
import org.d11.admin.jaxb.match.Player;
import org.d11.admin.jaxb.match.PlayerMatchStat;
import org.d11.admin.jaxb.match.PlayerMatchStats;
import org.d11.admin.jaxb.match.Substitution;
import org.d11.admin.jaxb.match.Substitutions;
import org.d11.admin.jaxb.match.Team;
import org.d11.admin.jaxb.match.Teams;
import org.d11.admin.parser.match.CardParserObject;
import org.d11.admin.parser.match.GoalParserObject;
import org.d11.admin.parser.match.PlayerParserObject;
import org.d11.admin.parser.match.SubstitutionParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredCardParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredGoalParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredPlayerParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredSubstitutionParserObject;
import org.d11.admin.parser.whoscored.match.WhoScoredTeamParserObject;
import org.joda.time.format.DateTimeFormat;

public class WhoScoredMatchStatisticsJAXBFileWriter extends JAXBFileWriter<WhoScoredMatchParserObject> {

	private Map<Integer, Team> teamMap = new HashMap<Integer, Team>();
	private Map<Integer, PlayerMatchStat> playerMatchStatMap = new HashMap<Integer, PlayerMatchStat>();

	public WhoScoredMatchStatisticsJAXBFileWriter() {
		setXmlRootClass(Match.class);
	}

	@Override
	protected JAXBElement buildDocument(List<WhoScoredMatchParserObject> whoScoredMatchParserObjects) {
		Match match = new Match();
		WhoScoredMatchParserObject whoScoredMatchParserObject = whoScoredMatchParserObjects.iterator().next();
		match.setWhoScoredId(BigInteger.valueOf(whoScoredMatchParserObject.getWhoScoredId()));
		match.setDateTime(whoScoredMatchParserObject.getDateTime().toString(DateTimeFormat.forPattern("YYYY-MM-dd HH:mm")));
		match.setTimeElapsed(whoScoredMatchParserObject.getTimeElapsed());

		match.setTeams(buildTeamsNode(whoScoredMatchParserObject));
		match.setPlayerMatchStats(buildPlayerMatchStatsNode(whoScoredMatchParserObject));
		match.setGoals(buildGoalsNode(whoScoredMatchParserObject));
		match.setCards(buildCardsNode(whoScoredMatchParserObject));
		match.setSubstitutions(buildSubstitutionsNode(whoScoredMatchParserObject));

		ObjectFactory objectFactory = new ObjectFactory();
		return objectFactory.createMatch(match);
	}

	private Teams buildTeamsNode(WhoScoredMatchParserObject whoScoredMatchParserObject) {
		Teams teams = new Teams();
		WhoScoredTeamParserObject whoScoredTeamParserObject = (WhoScoredTeamParserObject) whoScoredMatchParserObject.getHomeTeam();
		Team team = new Team();
		team.setWhoScoredId(BigInteger.valueOf(whoScoredTeamParserObject.getWhoScoredId()));
		team.setName(whoScoredTeamParserObject.getName());
		teams.setHomeTeam(team);
		teamMap.put(whoScoredTeamParserObject.getWhoScoredId(), team);

		whoScoredTeamParserObject = (WhoScoredTeamParserObject) whoScoredMatchParserObject.getAwayTeam();
		team = new Team();
		team.setWhoScoredId(BigInteger.valueOf(whoScoredTeamParserObject.getWhoScoredId()));
		team.setName(whoScoredTeamParserObject.getName());
		teams.setAwayTeam(team);
		teamMap.put(whoScoredTeamParserObject.getWhoScoredId(), team);

		return teams;
	}

	private PlayerMatchStats buildPlayerMatchStatsNode(WhoScoredMatchParserObject whoScoredMatchParserObject) {
		PlayerMatchStats playerMatchStats = new PlayerMatchStats();

		playerMatchStats.getPlayerMatchStat().addAll(buildPlayerMatchStatNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getHomeTeam()));
		playerMatchStats.getPlayerMatchStat().addAll(buildPlayerMatchStatNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getAwayTeam()));

		return playerMatchStats;
	}

	private Set<PlayerMatchStat> buildPlayerMatchStatNodes(WhoScoredTeamParserObject whoScoredTeamParserObject) {
		Set<PlayerMatchStat> playerMatchStats = new HashSet<PlayerMatchStat>();
		int momRating = 0;
		Set<PlayerMatchStat> moms = new HashSet<PlayerMatchStat>();

		for (PlayerParserObject playerParserObject : whoScoredTeamParserObject.getPlayers()) {
			WhoScoredPlayerParserObject whoScoredPlayerParserObject = (WhoScoredPlayerParserObject) playerParserObject;
			PlayerMatchStat playerMatchStat = new PlayerMatchStat();
			Player player = new Player();
			player.setWhoScoredId(BigInteger.valueOf(whoScoredPlayerParserObject.getWhoScoredId()));
			player.setName(whoScoredPlayerParserObject.getName());
			playerMatchStat.setPlayer(player);

			Team team = new Team();
			team.setWhoScoredId(BigInteger.valueOf(whoScoredTeamParserObject.getWhoScoredId()));
			team.setName(whoScoredTeamParserObject.getName());
			playerMatchStat.setTeam(team);

			playerMatchStat.setLineup(whoScoredPlayerParserObject.getParticipated());
			playerMatchStat.setRating(whoScoredPlayerParserObject.getRating());
			playerMatchStat.setGoalAssists(whoScoredPlayerParserObject.getAssists());
			playerMatchStat.setPlayedPosition(whoScoredPlayerParserObject.getPlayedPosition());
			playerMatchStat.setPosition(whoScoredPlayerParserObject.getPosition());

			playerMatchStats.add(playerMatchStat);
			playerMatchStatMap.put(player.getWhoScoredId().intValue(), playerMatchStat);

			if (playerMatchStat.getRating() == momRating) {
				moms.add(playerMatchStat);
			} else if (playerMatchStat.getRating() > momRating) {
				momRating = playerMatchStat.getRating();
				moms.clear();
				moms.add(playerMatchStat);
			}
		}

		for (PlayerMatchStat playerMatchStat : moms) {
			if (moms.size() == 1) {
				playerMatchStat.setManOfTheMatch(true);
			} else {
				playerMatchStat.setSharedManOfTheMatch(true);
			}
		}
		return playerMatchStats;
	}

	private Substitutions buildSubstitutionsNode(WhoScoredMatchParserObject whoScoredMatchParserObject) {
		Substitutions substitutions = new Substitutions();

		substitutions.getSubstitution().addAll(buildSubstitutionNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getHomeTeam()));
		substitutions.getSubstitution().addAll(buildSubstitutionNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getAwayTeam()));

		return substitutions;
	}

	private Set<Substitution> buildSubstitutionNodes(WhoScoredTeamParserObject whoScoredTeamParserObject) {
		Set<Substitution> substitutions = new HashSet<Substitution>();

		for (SubstitutionParserObject substitutionParserObject : whoScoredTeamParserObject.getSubstitutions()) {
			WhoScoredSubstitutionParserObject whoScoredSubstitutionParserObject = (WhoScoredSubstitutionParserObject) substitutionParserObject;
			Substitution substitution = new Substitution();

			PlayerMatchStat playerMatchStatOff = playerMatchStatMap.get(whoScoredSubstitutionParserObject.getPlayerOutWhoScoredId());
			PlayerMatchStat playerMatchStatOn = playerMatchStatMap.get(whoScoredSubstitutionParserObject.getPlayerInWhoScoredId());

			substitution.setPlayerOff(playerMatchStatOff.getPlayer());
			substitution.setPlayerOn(playerMatchStatOn.getPlayer());
			substitution.setTeam(playerMatchStatOff.getTeam());
			substitution.setTime(whoScoredSubstitutionParserObject.getTime());

			playerMatchStatOff.setSubstitutionOffTime(substitution.getTime());
			playerMatchStatOn.setSubstitutionOnTime(substitution.getTime());

			substitutions.add(substitution);
		}
		return substitutions;
	}

	private Goals buildGoalsNode(WhoScoredMatchParserObject whoScoredMatchParserObject) {
		Goals goals = new Goals();

		goals.getGoal().addAll(buildGoalNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getHomeTeam()));
		goals.getGoal().addAll(buildGoalNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getAwayTeam()));

		return goals;
	}

	private Set<Goal> buildGoalNodes(WhoScoredTeamParserObject whoScoredTeamParserObject) {
		Set<Goal> goals = new HashSet<Goal>();

		for (GoalParserObject goalParserObject : whoScoredTeamParserObject.getGoals()) {
			WhoScoredGoalParserObject whoScoredGoalParserObject = (WhoScoredGoalParserObject) goalParserObject;
			Goal goal = new Goal();

			PlayerMatchStat playerMatchStat = playerMatchStatMap.get(whoScoredGoalParserObject.getWhoScoredId());

			goal.setPlayer(playerMatchStat.getPlayer());
			goal.setTeam(teamMap.get(whoScoredGoalParserObject.getTeamId()));
			goal.setTime(whoScoredGoalParserObject.getTime());
			goal.setPenalty(whoScoredGoalParserObject.isPenalty());
			goal.setOwnGoal(whoScoredGoalParserObject.isOwnGoal());

			if (goal.isOwnGoal()) {
				playerMatchStat.setOwnGoals(playerMatchStat.getOwnGoals() + 1);
			} else {
				playerMatchStat.setGoalsScored(playerMatchStat.getGoalsScored() + 1);
			}

			for (PlayerMatchStat playerMatchStat2 : playerMatchStatMap.values()) {
				if (!playerMatchStat2.getTeam().getWhoScoredId().equals(goal.getTeam().getWhoScoredId())) {
					playerMatchStat2.setGoalsConceded(playerMatchStat2.getGoalsConceded() + 1);
				}
			}
			goals.add(goal);
		}
		return goals;
	}

	private Cards buildCardsNode(WhoScoredMatchParserObject whoScoredMatchParserObject) {
		Cards cards = new Cards();

		cards.getCard().addAll(buildCardNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getHomeTeam()));
		cards.getCard().addAll(buildCardNodes((WhoScoredTeamParserObject) whoScoredMatchParserObject.getAwayTeam()));

		return cards;
	}

	private Set<Card> buildCardNodes(WhoScoredTeamParserObject whoScoredTeamParserObject) {
		Set<Card> cards = new HashSet<Card>();

		for (CardParserObject cardParserObject : whoScoredTeamParserObject.getCards()) {
			WhoScoredCardParserObject whoScoredCardParserObject = (WhoScoredCardParserObject) cardParserObject;
			Card card = new Card();

			PlayerMatchStat playerMatchStat = playerMatchStatMap.get(whoScoredCardParserObject.getWhoScoredId());

			card.setPlayer(playerMatchStat.getPlayer());
			card.setTeam(playerMatchStat.getTeam());
			card.setTime(whoScoredCardParserObject.getTime());
			card.setCardType((whoScoredCardParserObject.getCardType() == CardParserObject.CardType.YELLOW ? CardType.YELLOW : CardType.RED));

			if (card.getCardType().equals(CardType.YELLOW)) {
				playerMatchStat.setYellowCardTime(card.getTime());
			} else {
				playerMatchStat.setRedCardTime(card.getTime());
			}

			cards.add(card);
		}
		return cards;
	}
}
