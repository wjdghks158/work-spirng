package com.bit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class ConnectionPool {
	// �뿰寃고븷 �닔 �엳�뒗 理쒕� �옄�썝�쓣 5媛쒕줈 �븳�젙
	private static final int INIT_COUNT=5;
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/hellodb?serverTimezone=UTC";
	// �궗�슜 媛��뒫�븳 Connection 媛앹껜 �젙蹂대�� �떞�뒗 由ъ뒪�듃
	private static ArrayList<Connection> freeList = new ArrayList<>();
	// �궗�슜 以묒씤 Connection 媛앹껜 �젙蹂대�� �떞�뒗 由ъ뒪�듃
	private static ArrayList<Connection> usedList = new ArrayList<>();
	
	// Static Block
	static {
		// 珥덇린�솕 �옉�뾽 : 5媛쒖쓽 Connection瑜� �깮�꽦�븯�뿬 �궗�슜 媛��뒫�븳 由ъ뒪�듃�뿉 �떞�븘�씪.
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
		// �뜑 �씠�긽 �궗�슜 媛��뒫�븳 Connection�씠 �뾾�떎硫�, �삁�쇅 諛쒖깮!
		if(freeList.isEmpty()) {
			throw new Exception("�궗�슜 媛��뒫�븳 Connection�씠 �궓�븘�엳吏� �븡�뒿�땲�떎.");
		}//end if
		
		// remove() : �떒�닚�엳 �궘�젣媛� �븘�땲�씪, �궘�젣 �썑 諛섑솚源뚯�! (getConnection�맂 con�쓣 list�뿉 �궘�젣 �썑 諛섑솚?)
		Connection con = freeList.remove(0); // �궘�젣 諛� 諛섑솚 -> freeList �궘�젣 �썑 諛섑솚 諛쏆븘,
		usedList.add(con);  // userList�뿉 異붽�!
		//	-> �궘�젣�맂 媛앹껜瑜� �떎瑜� 由ъ뒪�듃�뿉 �궗�슜!
		
		System.out.println("free Connecton : "+freeList.size()); //4
		System.out.println("used Connection : "+usedList.size());
		
		return con;
	}//
	
	public static void close(Connection con) {
		usedList.remove(usedList.indexOf(con));
		freeList.add(con);
	}
	
}//class
