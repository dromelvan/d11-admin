package org.d11.admin.model;

public class Team extends PersistentD11Model {

	private String name;

	public Team(int id, String name) {
		setId(id);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
