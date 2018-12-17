package ua.khai.kharkov.provider.db.entity;

import java.io.Serializable;

public class Entity implements Serializable {
	private static final long serialVersionUID = 8466257860808346236L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
