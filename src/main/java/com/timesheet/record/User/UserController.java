package com.timesheet.record.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timesheet.record.exception.ResourceFound;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin
@RestController
public class UserController {

	// URL PATH ----
	// METHOD -- GET POST , PUT
	// REQUEST BODY / PATH VARIABLE
	// RESPOSNSE BODY
	// HTTP STATUS NO.

	// view -------------- get
	// view1--------------
	// delete------------- delete
	// save-- post
	// update--put

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello World!");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public List<User> viewUsers() {
		return userService.viewAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
	public ResponseEntity<User> viewUser(@PathVariable String username) {
		User user = userService.findByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
	public ResponseEntity<User> deleteUser(@PathVariable String username) {
		userService.delete(username);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws ResourceFound {
		userService.save(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
	public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
		userService.update(username, user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
