package com.bridgelabz.loginregistration.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.loginregistration.model.User;
import com.bridgelabz.loginregistration.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;

/**
 * Purpose : To control all API services.
 * 
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    11/07/2018
 */
@RestController
public class userController {

	@Autowired
	private UserServiceImpl userService;

	/**
	 * Method is written to get all the users details from the database.
	 * 
	 * @return List<Users>
	 */
	@ApiOperation(value = "Get all user details")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUserDetails() {
		return userService.getUserDetails();
	}

	/**
	 * Method is written to receive the user object sent from the postman.
	 * 
	 * @param user
	 * @return ResponseEntity<User>
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<User> signUp(@RequestBody User user, HttpServletResponse res) throws Exception {
		
		userService.signUp(user);
		return new ResponseEntity("Mail sent to the user with email " + user.getEmail(), HttpStatus.OK);
	}

	/**
	 * Method is written to receive the user object to get user userName and
	 * password to log in after validation. An to generate token if successful
	 * logged in.
	 * 
	 * @param user
	 * @param res
	 * @return ResponseEntity<User>
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> logIn(@RequestBody User user, HttpServletResponse res) throws Exception {
		userService.logIn(user.getEmail(),user.getPassword());
		return new ResponseEntity<>(" Welcome " + user.getEmail(), HttpStatus.CONFLICT);
	}

	/**
	 * Method is written to activate the User after user is validated as a true
	 * owner of the email by using the token.
	 * 
	 * @param req
	 * @return String
	 */
	@RequestMapping(value = "/activationlink", method = RequestMethod.GET)
	public ResponseEntity<String> activation(HttpServletRequest req) {
		userService.claimToken(req.getQueryString());
		return new ResponseEntity<>("Your account is successfully activated", HttpStatus.OK);
	}

	/**
	 * Method is written to recover the account of the user if user forget their
	 * password by taking their email as an input
	 * 
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/recoveraccount", method = RequestMethod.POST)
	public ResponseEntity<String> passwordRecover(@RequestBody User user) throws Exception {
		userService.passwordRecover(user.getEmail());
		return new ResponseEntity("Your Password Is Sent To Your Mail,Please Check It.. ", HttpStatus.OK);
	}

}
