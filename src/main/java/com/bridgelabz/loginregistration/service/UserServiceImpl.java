package com.bridgelabz.loginregistration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.loginregistration.exception.LoginException;
import com.bridgelabz.loginregistration.exception.RegistrationException;
import com.bridgelabz.loginregistration.model.User;
import com.bridgelabz.loginregistration.repository.UserRepository;
import com.bridgelabz.loginregistration.tokenutility.TokenUtility;

/**
 * Purpose : To provide services for User class.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 11/07/2018
 */
@Service
public class UserServiceImpl {

	// RepositoryImpl repository=new RepositoryImpl();

	@Autowired
	TokenUtility token;

	@Autowired
	UserRepository repository;

	@Autowired
	Mail mail;

	@Autowired
	MailService mailService;

	/**
	 * @see com.bridgelabz.loginregistration.service.UserService#getUserDetails()
	 * 
	 * Method is defined to get all user present in the database.
	 * @return List<User>
	 */
	public List<User> getUserDetails() {
//		return repository.getUserDetails();
		return repository.findAll();
	}

	public void signUp(User user) throws Exception {
		String email=user.getEmail();
		System.out.println("checking" + email);

		if (!email.equals("")) {
			System.out.println("email not null");

			Optional<User> dbUser = repository.findByEmail(email);
			if (dbUser.isPresent()) {
				// System.out.println("User name already exist");
				throw new RegistrationException(email + "User Allready Exist");
			}
			
		System.out.println("Generating Token");
		String validToken = token.generator(user);
		repository.save(user);
		mail.setSubject("Account Confirmation Link");
		mail.setBody("Click the given below to activate your account \n\n "
				+ "http://192.168.0.61:8080//activationlink/?" + validToken);
		mail.setTo(user.getEmail());
		mailService.sendMail(mail);
		// MailSender.sendMail(user.getEmail(), validToken, null);
		System.out.println("Mail Sent");
		return;
		}
		System.out.println("Throwing exception");
		throw new Exception("Email cannot be null");
	}

	public void logIn(String email, String password) throws Exception {
		// utility.validateUser(user.getEmail(), user.getPassword());
		System.out.println("validating user");
		System.out.println(email);
		System.out.println(password);
		if (!email.equals("") && !password.equals("")) {

			Optional<User> user = repository.findByEmail(email);
			System.out.println("Email founded");
			if (user.isPresent()) {
				if (!user.get().getStatus().equals("false")) {
					if ((user.get().getPassword()).equals(password)) {
						System.out.println("Password Matched");
						System.out.println("validating user true");
						return;
					}
					System.out.println("Password Incorrect");
					throw new LoginException("Password is incorrect");
				} else {
					throw new Exception("Go to your mail and click on the link first to validate your account");
				}
			}
			throw new LoginException("Email  is wrong ");
		}
		throw new LoginException("Email and password cannot be null");
	}

	/**
	 * Method is written to activate user account. As soon as user clicked the link
	 * sent to them their status in the database will be changed to true.
	 * 
	 * @param email
	 */
	public void claimToken(String claimedToken) {
		User newUser = new User();
		Optional<User> user = repository.findByEmail(token.parseJWT(claimedToken));
		newUser.setId(user.get().getId());
		newUser.setUserName(user.get().getUserName());
		newUser.setEmail(user.get().getEmail());
		newUser.setPhoneNumber(user.get().getPhoneNumber());
		newUser.setPassword(user.get().getPassword());
		newUser.setStatus("true");
		repository.save(newUser);
	}

/*	*//**
	 * @see com.bridgelabz.loginregistration.service.UserService#checkUserName(java.lang.String)
	 *
	 * Method is written to check whether user with entered userName is present
	 * in the database . If present return true or if not return false.
	 * @param userName
	 * @return boolean
	 * @throws Exception
	 *//*
	public void checkUserName(String email) throws Exception {
		// return repository.checkUserName(userName);
		
			return;
		}*/

	/**
	 * Method is written to recover the password of the user in case if they forget
	 * their password. Password will be sent to the user email.
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public void passwordRecover(String email) throws Exception {
		if (!email.equals("")) {
			Optional<User> user = repository.findByEmail(email);
			if (user.isPresent()) {
				String password = user.get().getPassword();
				mail.setSubject("Password Recovry Mail");
				mail.setBody("User name : " + email + "\n Your pasword is " + password);
				mail.setTo(email);
				mailService.sendMail(mail);
				// MailSender.sendMail(email, null, password);
				return;
			}
			throw new Exception("Email Not Valid,Please Re Write Or SignUp First");
		}
		throw new Exception("Email Cannot Be Null");
	}
}
