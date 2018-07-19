package org.d11.admin.model.premierleague;

import org.d11.admin.model.Player;
import org.joda.time.LocalDate;

public class PLPlayer extends Player {

	public PLPlayer(int id, String name, int number, String team, String position, String nationality, String imageId, LocalDate dateOfBirth, int height) {
		super(id, name, number, team, position, nationality, imageId);
		setDateOfBirth(dateOfBirth);
		setHeight(height);
	}

}
