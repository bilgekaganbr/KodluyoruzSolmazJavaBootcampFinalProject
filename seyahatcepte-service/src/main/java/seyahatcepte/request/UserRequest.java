package seyahatcepte.request;

import seyahatcepte.model.enums.UserType;

public class UserRequest {
	
	private String userName;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String phoneNumber;
	private UserType type;
	
	
	public UserRequest() {
		super();
	}


	public UserRequest(String userName, String name, String surname, String email, String password, 
			String phoneNumber, UserType type) {
		super();
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.type = type;
	}

	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public UserType getType() {
		return type;
	}


	public void setType(UserType type) {
		this.type = type;
	}
	
	
	
	

}
