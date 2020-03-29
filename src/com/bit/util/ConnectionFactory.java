package com.bit.util;

import java.sql.Connection;
import java.sql.DriverManager;

// �궗�슜 �떆�뿉留� 諛쏆븘�삤�룄濡� Factory(�뙥�넗由�) �젙�쓽
public class ConnectionFactory {
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url 		= "jdbc:mysql://127.0.0.1:3306/insight?serverTimezone=UTC";
			String user = "root";
			String password = "root";
			
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}







