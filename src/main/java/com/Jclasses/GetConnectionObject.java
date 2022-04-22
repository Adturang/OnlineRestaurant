package com.Jclasses;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class GetConnectionObject {
	
	public Connection getConnectionObj() throws Exception {
		FileInputStream fis=new FileInputStream("url&pass.properties");
		Properties pr=new Properties();
		pr.load(fis);
		String driver=pr.getProperty("drLoad");
		String url=pr.getProperty("URL");
		String user=pr.getProperty("user");
		String pass=pr.getProperty("pass");
		Connection CON=null;
		Class.forName(driver);
		CON=DriverManager.getConnection(url,user,pass);
		return CON;
	}
	
}
