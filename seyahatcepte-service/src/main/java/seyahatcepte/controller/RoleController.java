package seyahatcepte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import seyahatcepte.model.Role;
import seyahatcepte.response.UserResponse;
import seyahatcepte.service.RoleService;

public class RoleController {

	@Autowired
	private RoleService roleService;

	
	@PostMapping(value = "/roles/create")
	public Role create(@RequestBody Role role) {
		
		return roleService.create(role);
	}
	
	@PutMapping(value = "/security/role/assign/{userName}/{roleId}")
	public UserResponse assign(@PathVariable String userName, @PathVariable String roleId) {
		
		return roleService.assignRole(userName, roleId);
	}
}
