package com.videotweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection openConnection() throws ClassNotFoundException, SQLException{
		
		  String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://tweetvideousers.cngwtgfvvaab.us-west-2.rds.amazonaws.com:3306/TweetVideo";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "vthallam", "Steve123");
	      System.out.print("Connection created");
	      return conn;
	}
}
