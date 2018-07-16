package com.bridgelabz.loginregistration.loggers;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MyLogger {
	public  void m1() {
	//Logger.getLogger("global");	
	Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//Handler handler=logger.getHandlers();
	//	logger.addHandler("vdfsv");
//	Handler handler=new Handler();
	//logger.addHandler(null);
	logger.info("email not found");
	logger.log(Level.SEVERE,"rtgsegaegsdfgdsfgds");
	}
	
	public static void main(String args[]) {
		MyLogger m=new MyLogger();
		m.m1();
	}
}
