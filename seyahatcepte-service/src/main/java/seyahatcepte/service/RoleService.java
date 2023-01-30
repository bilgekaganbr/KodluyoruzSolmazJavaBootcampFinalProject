package seyahatcepte.service;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import seyahatcepte.controller.RoleController;
import seyahatcepte.converter.UserConverter;
import seyahatcepte.exception.RoleNotFoundException;
import seyahatcepte.exception.UserNotFoundException;
import seyahatcepte.model.Role;
import seyahatcepte.model.User;
import seyahatcepte.repository.RoleRepository;
import seyahatcepte.repository.UserRepository;
import seyahatcepte.response.UserResponse;

public class RoleService {

	Logger logger = Logger.getLogger(RoleController.class.getName());

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	// create role
	public Role create(Role role) {

		return roleRepository.save(role);
	}

	// assign role to user
	public UserResponse assignRole(String userName, String roleName) {

		User user = userRepository.findById(userName).orElseThrow(() -> new UserNotFoundException("User not found!"));
		Role role = roleRepository.findById(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found!"));

		logger.log(Level.INFO, "[foundUserAndRole] - user found: {0}, role found: {1}",
				new Object[] { userName, roleName });

		Set<Role> userRoles = user.getRoles();
		userRoles.add(role);
		user.setRoles(userRoles);

		logger.log(Level.INFO, "[assignRole] - role {0} is assigned to user {1}", new Object[] { userName, roleName });

		return userConverter.convert(userRepository.save(user));
	}
}
