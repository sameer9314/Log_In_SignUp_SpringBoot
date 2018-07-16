package com.bridgelabz.loginregistration.utility;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Purpose : To get the mongoDatabase connection.	
 * @author Sameer Saurabh
 * @version  1.0
 * @Since  11/07/2018
 */
public class LogInUtility {

	
	/**
	 * This method is written to get the connection from mongoDatabase
	 * and return the collection.
	 * @returntype  DBCollection
	 */
	public static DBCollection getCollection() {
		//Create connection
		System.out.println("Creating connection with mongoDb Server");
		MongoClient mongoClient=new MongoClient("localhost",27017);
		System.out.println("Connection Established");
						
		//Connecting with database.
		System.out.println("Connecting with datbase");
		@SuppressWarnings("deprecation")
		DB db=mongoClient.getDB("logInRegistration");
		System.out.println("Connected with Db");
		System.out.println("Database name : "+db.getName());	
				
		// Creating new collection.
		DBCollection coll=db.getCollection("user_detail");
		System.out.println("Collection created");
				
		return coll;
	}
}
