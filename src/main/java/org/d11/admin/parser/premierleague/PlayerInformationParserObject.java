package org.d11.admin.parser.premierleague;

import org.joda.time.LocalDate;

public class PlayerInformationParserObject extends PlayerParserObject {

	private int number;
	private String team;
	private String position;
	private String nationality;
	private LocalDate dateOfBirth;
	private int height;
	private String imageId;

	public PlayerInformationParserObject() {
	}

	public PlayerInformationParserObject(String id, String name, int number, String team, String position, String nationality, LocalDate dateOfBirth, int height, String imageId) {
		super(id, name);
		this.number = number;
		this.team = team;
		this.position = position;
		this.nationality = nationality;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.imageId = imageId;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Override
	public String toString() {
		return String.format("PlayerInformation { Id: %s, Name: %s, Number: %d, Team: %s, Position: %s, Nationality: %s, DateOfBirth: %s, Height: %d, ImageId: %s }",
				getId(),
				getName(),
				getNumber(),
				getTeam(),
				getPosition(),
				getNationality(),
				(getDateOfBirth() != null ? getDateOfBirth() : "Unknown"),
				getHeight(),
				getImageId());
	}

}
