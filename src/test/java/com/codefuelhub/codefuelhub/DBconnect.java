package com.codefuelhub.codefuelhub;

import java.sql.*;

public class DBconnect {

	Connection con;
	
	public void establishConnection() {
		String host = "jdbc:mysql://ccmobile-dev.cztkqn8oiusx.us-east-1.rds.amazonaws.com:3306/";
		String user = "PortalUser";
		String password = "q!w2e3R$";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =  DriverManager.getConnection(host, user, password);
		} catch (SQLException e) {
			System.out.println("Connection to DB was unsuccessful, " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("SQL class wasn't found, " + e.getMessage());
		}
	}
	
	public void executeCmd(String cmd){
		try{
			Statement st = con.createStatement();
		
			st.executeUpdate(cmd);
			
		} catch (SQLException e) {
			System.out.println("The cmd execute was unsuccessful" + e.getMessage());
		}
		
	}
}
