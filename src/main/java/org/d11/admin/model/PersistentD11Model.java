package org.d11.admin.model;

public class PersistentD11Model extends D11Model {

	private Integer id;

	public PersistentD11Model() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
	    if(object instanceof PersistentD11Model) {
	        PersistentD11Model persistentD11Model = (PersistentD11Model)object;
	        return getClass().equals(persistentD11Model.getClass()) && getId() == persistentD11Model.getId();
	    }
	    return false;
	}
}
