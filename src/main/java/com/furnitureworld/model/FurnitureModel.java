package com.furnitureworld.model;

public class FurnitureModel {
	private int register_id;
	private String full_name;
	private String email;
	private String username;
	private String password;
	private boolean is_Admin;
	
	
	public FurnitureModel() {}
	
	public FurnitureModel(int register_id, String full_name, String email, String username, String password, boolean is_Admin) {
		super();
		this.register_id = register_id;
		this.full_name = full_name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.is_Admin = false;
	}
	
	public FurnitureModel(String full_name, String email, String username, String password){
		super();
		this.full_name = full_name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public FurnitureModel(String username, String password ) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public int getRegister_id() {
		return register_id;
	}
	public void setRegister_id(int register_id) {
		this.register_id = register_id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIs_Admin() {
		return is_Admin;
	}

	public void setIs_Admin(boolean is_Admin) {
		this.is_Admin = is_Admin;
	}
	
	
}
