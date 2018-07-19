package org.d11.admin.model;

public class Player extends PersistentD11Model {

	private Integer whoscored_id;
	private String name;
	private Integer number;
	private String team;
	private String position;
	private String nationality;
	private Integer height;
	private Integer weight;
	private String date_of_birth;

	public Player() {
	}

	public int getWhoScoredId() {
		return whoscored_id;
	}

	public void setWhoScoredId(int whoScoredId) {
		this.whoscored_id = whoScoredId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDateOfBirth() {
		return date_of_birth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.date_of_birth = dateOfBirth;
	}

	public String getParameterizedName() {
		return parameterize(getName());
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Player) {
			Player player = (Player) object;
			return getId() == player.getId();
		}
		return false;
	}

}
