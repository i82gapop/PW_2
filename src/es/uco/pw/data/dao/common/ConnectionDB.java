package es.uco.pw.data.dao.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDB {

    public static Connection getConnection() throws IOException{

		Connection con=null;
		
		Properties properties = new Properties();
		
		InputStream properties_file = new FileInputStream("properties.properties");
		
		properties.load(properties_file);
		
		String url = properties.getProperty("Url");
		String user = properties.getProperty("User");
		String pwd = properties.getProperty("Pwd");
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con= DriverManager.getConnection(url,user,pwd);
		} catch(Exception e) {
			
			System.out.println(e);
		}
		
		return con;
	}
}
