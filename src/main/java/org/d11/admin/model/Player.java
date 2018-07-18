package org.d11.admin.model;

public class Player extends PersistentD11Model {

	private Integer whoscored_id;
	private String name;
	private Integer number;
	private String team;
	private String position;
	private String nationality;
	private String imageId;

	public Player() {
	}

	public Player(int id, String name) {
		setId(id);
		this.name = name;
	}

	public Player(int id, String name, int number, String team, String position, String nationality, String imageId) {
		setId(id);
		this.name = name;
		this.number = number;
		this.team = team;
		this.position = position;
		this.nationality = nationality;
		this.imageId = imageId;
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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
