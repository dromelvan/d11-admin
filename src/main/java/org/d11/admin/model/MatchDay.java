package org.d11.admin.model;

public class MatchDay extends PersistentD11Model {

    private Integer status;
	private int match_day_number;
	private int[] match_ids;

	public MatchDay() {
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getMatchDayNumber() {
		return match_day_number;
	}

	public void setMatchDayNumber(int matchDayNumber) {
		this.match_day_number = matchDayNumber;
	}

	public int[] getMatchIds() {
		return match_ids;
	}

	public void setMatchIds(int[] matchIds) {
		this.match_ids = matchIds;
	}

}
