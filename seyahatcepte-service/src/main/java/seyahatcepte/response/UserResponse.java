package seyahatcepte.response;

import java.util.List;
import java.util.Set;

import seyahatcepte.model.Role;
import seyahatcepte.model.Ticket;
import seyahatcepte.model.enums.UserType;

public class UserResponse {
	
	private String userName;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private UserType type;
	private Set<Role> roles;
	private List<Ticket> ticketList;
	
	
	public UserResponse() {
		super();
	}


	public UserResponse(String userName, String name, String surname, String email,
			String phoneNumber, UserType type) {
		super();
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.email = email;
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

	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public List<Ticket> getTicketList() {
		return ticketList;
	}


	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}


	
	
	
	
	

}
