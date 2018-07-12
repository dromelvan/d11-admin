package org.d11.admin.model;

public enum Position {

	UNKNOWN(0, "Unknown"),
	GOALKEEPER(1, "Goalkeeper"),
	FULL_BACK(2, "Full back"),
	DEFENDER(3, "Defender"),
	MIDFIELDER(4, "Midfielder"),
	FORWARD(5, "Forward");

	private int id;
	private String name;

	private Position(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
