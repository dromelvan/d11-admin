package org.d11.admin.model;

public class Team extends PersistentD11Model {

	private String name;
	private Integer whoscored_id;

	public Team() {
	}

	public Team(int id, String name) {
		setId(id);
		setName(name);
	}

	public Team(int id, int whoScoredId, String name) {
		setId(id);
		setWhoScoredId(whoScoredId);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWhoScoredId() {
		return whoscored_id;
	}

	public void setWhoScoredId(int whoScoredId) {
		this.whoscored_id = whoScoredId;
	}

	public String getParameterizedName() {
		return parameterize(getName());
	}

}
