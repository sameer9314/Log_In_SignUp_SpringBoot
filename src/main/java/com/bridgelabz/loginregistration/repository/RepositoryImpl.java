package com.bridgelabz.loginregistration.repository;

import java.util.LinkedList;
import java.util.List;

import com.bridgelabz.loginregistration.model.User;
import com.bridgelabz.loginregistration.utility.LogInUtility;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;

/**
 * Purpose : Purpose to implement CRUD for mongoRepository.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 11/07/2018
 */
public class RepositoryImpl {

	/**
	 * Method is written to check whether the user with entered user name is present
	 * or not.
	 * 
	 * @param userName
	 * @return boolean
	 */
	public boolean checkUserName(String userName) {
		DBCollection collection = LogInUtility.getCollection();

		System.out.println("Finding details with userName");
		BasicDBObject ob = new BasicDBObject("userName", userName);
		Cursor cursor = collection.find(ob);

		while (cursor.hasNext()) {
			BasicDBObject b = (BasicDBObject) cursor.next();
			if (b.get("userName").toString().equals(userName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method is written to validate that the entered userName and Password is
	 * matching or not.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean validateUser(String userName, String password) {
		DBCollection collection = LogInUtility.getCollection();

		System.out.println("Finding details with userName");
		BasicDBObject ob = new BasicDBObject("userName", userName);
		Cursor cursor = collection.find(ob);

		while (cursor.hasNext()) {
			BasicDBObject b = (BasicDBObject) cursor.next();
			if (b.get("userName").toString().equals(userName) && b.get("password").toString().equals(password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method is written to store the user in the database.
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		DBCollection collection = LogInUtility.getCollection();

		// insert into collection.
		BasicDBObject student = new BasicDBObject("_id", user.getId()).append("userName", user.getUserName())
				.append("phoneNumber", user.getPhoneNumber()).append("password", user.getPassword());
		collection.insert(student);
		System.out.println("Document Inserted");
	}

	/**
	 * Method is written to get all the users details present in the database.
	 * 
	 * @return List<User>
	 */
	public List<User> getUserDetails() {
		List<User> userList = new LinkedList();
		DBCollection collection = LogInUtility.getCollection();
		Cursor cursor = collection.find();

		while (cursor.hasNext()) {
			User user = new User();
			BasicDBObject b = (BasicDBObject) cursor.next();
			user.setId(b.get("_id").toString());
			user.setUserName(b.get("userName").toString());
			user.setPhoneNumber(b.get("phoneNumber").toString());
			user.setPassword(b.get("password").toString());
			userList.add(user);

		}
		return userList;
	}

}
