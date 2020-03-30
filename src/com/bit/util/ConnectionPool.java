package com.bit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class ConnectionPool {
	private static final int INIT_COUNT=5;
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/hellodb?serverTimezone=UTC";
	private static ArrayList<Connection> freeList = new ArrayList<>();
	private static ArrayList<Connection> usedList = new ArrayList<>();
	
	// Static Block
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			for(int i = 0; i < INIT_COUNT; i++) { //5
				freeList.add(DriverManager.getConnection(URL,"root","root"));
			}//end for
		}catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//static
	
	public static Connection getConnection() throws Exception{
		if(freeList.isEmpty()) {
			throw new Exception("에러");
		}//end if
		
		Connection con = freeList.remove(0); 
		usedList.add(con);  

		return con;
	}//
	
	public static void close(Connection con) {
		usedList.remove(usedList.indexOf(con));
		freeList.add(con);
	}
	
}//class
