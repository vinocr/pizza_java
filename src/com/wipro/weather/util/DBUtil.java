package com.wipro.weather.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	public static Connection getDBConnection(){
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","B44575767521","B44575767521");
		}
		catch(Exception e){
			return null;
		}
	}
	
}
