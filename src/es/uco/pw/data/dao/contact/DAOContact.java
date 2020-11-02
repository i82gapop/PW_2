package es.uco.pw.data.dao.contact;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;

public class DAOContact {

	public static Connection getConnection() throws IOException{

		Connection con=null;
		
		Properties sql_properties = new Properties();
		
		InputStream sql_properties_file = new FileInputStream("sql.properties");
		
		sql_properties.load(sql_properties_file);
		
		String url = sql_properties.getProperty("Url");
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con= DriverManager.getConnection(url);
		} catch(Exception e) {
			
			System.out.println(e);
		}
		
		return con;
	}
	
	
}
