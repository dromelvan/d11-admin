package org.d11.admin.model.premierleague;

import org.d11.admin.model.Player;
import org.joda.time.LocalDate;

public class PLPlayer extends Player {

	private LocalDate dateOfBirth;
	private int height;

	public PLPlayer(int id, String name, int number, String team, String position, String nationality, String imageId, LocalDate dateOfBirth, int height) {
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

}
