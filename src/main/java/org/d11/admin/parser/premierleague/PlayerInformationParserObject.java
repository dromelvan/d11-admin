package org.d11.admin.parser.premierleague;

import org.joda.time.LocalDate;

public class PlayerInformationParserObject extends PlayerParserObject {

    private LocalDate dateOfBirth;
    private int height;

	public PlayerInformationParserObject() {
	}

	public PlayerInformationParserObject(String id, String name, int number, String team, String position, String nationality, LocalDate dateOfBirth, int height, String imageId) {
		super(id, name, number, team, position, nationality, imageId);
        this.dateOfBirth = dateOfBirth;
        this.height = height;

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
