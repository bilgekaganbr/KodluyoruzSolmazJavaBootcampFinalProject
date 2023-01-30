package seyahatcepte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import seyahatcepte.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	

}
