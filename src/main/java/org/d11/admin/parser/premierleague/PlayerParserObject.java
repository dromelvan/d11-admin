package org.d11.admin.parser.premierleague;

import org.d11.admin.parser.ParserObject;

public class PlayerParserObject extends ParserObject {

	private String id;
	private String name;
	private int number;
	private String team;
	private String position;
	private String nationality;
	private String imageId;

	public PlayerParserObject() {
	}

	public PlayerParserObject(String id, String name, int number, String team, String position, String nationality, String imageId) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.team = team;
		this.position = position;
		this.nationality = nationality;
		this.imageId = imageId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return String.format("Player { Id: %s, Name: %s, Number: %d, Team: %s, Position: %s, Nationality: %s, ImageId: %s }",
				getId(),
				getName(),
				getNumber(),
				getTeam(),
				getPosition(),
				getNationality(),
				getImageId());
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof PlayerParserObject) {
			PlayerParserObject playerParserObject = (PlayerParserObject) object;
			return getId() != null && playerParserObject.getId() != null && getId().equals(playerParserObject.getId());
		}
		return false;
	}

}
