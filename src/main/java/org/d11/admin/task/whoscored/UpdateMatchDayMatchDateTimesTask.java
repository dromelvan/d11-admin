package org.d11.admin.task.whoscored;

import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.task.D11Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UpdateMatchDayMatchDateTimesTask extends D11Task<Match> {

	private MatchDay matchDay;
	@Inject
	private Provider<UpdateMatchDateTimeTask> updateMatchDateTimeTaskProvider;
	private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDayMatchDateTimesTask.class);

	public UpdateMatchDayMatchDateTimesTask() {
	}

	public UpdateMatchDayMatchDateTimesTask(MatchDay matchDay) {
		this.matchDay = matchDay;
	}

	public MatchDay getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(MatchDay matchDay) {
		this.matchDay = matchDay;
	}

	@Override
	public boolean execute() {
		logger.debug("Updating match date times for match day {} ({})", getMatchDay().getMatchDayNumber(), getMatchDay().getId());
		int failures = 0;

		for (int matchId : getMatchDay().getMatchIds()) {
			UpdateMatchDateTimeTask updateMatchDateTimeTask = updateMatchDateTimeTaskProvider.get();
			updateMatchDateTimeTask.setMatchId(String.valueOf(matchId));
			updateMatchDateTimeTask.setMatchDayNumber(getMatchDay().getMatchDayNumber());
			if (!updateMatchDateTimeTask.execute()) {
				++failures;
			}
		}
		logger.debug("Finished updating match date times with {} failures.", failures);
		return failures == 0;
	}
}
