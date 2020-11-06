package es.uco.pw.data.dao.contact;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.data.dao.common.ConnectionDB;

public class DAOContact extends ConnectionDB{
	
	public static int Save(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("Insert");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			ps.setString(2,contact.getName());
			ps.setString(3,contact.getSurname());
			ps.setDate(4,contact.getBirthday());
			
			status = ps.executeUpdate();
	
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static int Update(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("Update");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getName());
			ps.setString(2,contact.getSurname());
			ps.setDate(3,contact.getBirthday());
			ps.setString(4,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static int Delete(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("Delete");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static ArrayList <Contact> ListContacts(){

		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"));

				resul.add(aux);
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	}

	public static Contact QueryByEmail (Contact contact){
		
		Statement stmt = null; 
		Contact resul = null;
		
		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByEmail");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getEmail() + "'");
		    
		    while (rs.next()) {
		    	
		    	resul = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"));
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close();
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	} 

	public static ArrayList <Contact> QueryByName (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByName");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getName() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), null, rs.getString("Email"));
		    	
				resul.add(aux);
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	} 
	
	public static ArrayList <Contact> QueryBySurname (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryBySurname");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getSurname() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), null, rs.getString("Email"));

				resul.add(aux);
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	} 

	public static ArrayList <Contact> QueryByFullname (Contact contact){
		
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null; 

		try {
			
			Connection con=getConnection();

			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByFullname");
			
			PreparedStatement stmt = con.prepareStatement(statement);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getSurname());
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {

		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), null, rs.getString("Email"));

				resul.add(aux);
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		}

		return resul;
	}

	public static ArrayList <Contact> QueryByAge (int Age){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
				aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"));
				
				if(aux.getAge() == Age){

					resul.add(aux);
				}
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	}
}
