package com.todo.resource;

import com.todo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.entities.User;
import com.todo.repositories.UserRepository;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable UUID id) {
		log.info("findById");
		User obj = repository.findById(id).get();
		return ResponseEntity.ok(obj);
	}	
	
	@GetMapping(value = "/search")
	public ResponseEntity<User> findByEmail(@RequestParam String email) {
		log.info("search email");
		//User obj = repository.findByEmail(email);
		return ResponseEntity.ok(loginService.findUserByEmail(email));
	}
}
