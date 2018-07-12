package org.d11.admin.model;

public class PersistentD11Model extends D11Model {

	private int id;

	public PersistentD11Model(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
