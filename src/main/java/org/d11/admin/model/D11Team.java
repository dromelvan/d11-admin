package org.d11.admin.model;

public class D11Team extends PersistentD11Model {

	private String name;

	public D11Team() {
	}

	public D11Team(int id, String name) {
		setId(id);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameterizedName() {
		return parameterize(getName());
	}

}
