package seyahatcepte.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import seyahatcepte.model.User;
import seyahatcepte.request.UserRequest;
import seyahatcepte.response.UserResponse;

@Component
public class UserConverter {
	
	//convert UserRequest to User
	public User convert(UserRequest userRequest /*String hash*/) {
		
		User user = new User();
		
		user.setUserName(userRequest.getUserName());
		user.setName(userRequest.getName());
		user.setSurname(userRequest.getSurname());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setPhoneNumber(userRequest.getPhoneNumber());
		user.setType(userRequest.getType());
		user.setCreateDate(LocalDateTime.now());
		
		return user;
	}
	
	//convert User to UserResponse
	public UserResponse convert(User user) {
		
		UserResponse userResponse = new UserResponse();
		
		userResponse.setUserName(user.getUserName());
		userResponse.setName(user.getName());
		userResponse.setSurname(user.getSurname());
		userResponse.setEmail(user.getEmail());
		userResponse.setPhoneNumber(user.getPhoneNumber());
		userResponse.setType(user.getType());
		userResponse.setRoles(user.getRoles());
		userResponse.setTicketList(user.getTicketList());
		
		
		return userResponse;
	}
	
	//convert list of User to list of UserResponse
	public List<UserResponse> convert(List<User> userList) {
		
		List<UserResponse> userResponses = new ArrayList<>();

		for (User user : userList) {
			userResponses.add(convert(user));
		}

		return userList.stream().map(this::convert).toList();
	}
	
}
