package com.student.manager.common;

public enum Department {
	
	CSE("Computers Sciences Engineering"),
	ECE("Electronics and Communications Engineering"),
	CE("Civil Engineering"),
	ME("Mechanical Engineering"),
	EEE("Electrics and Electronics Engineering");
	
	private String description;
	
	Department(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}

