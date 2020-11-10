package es.uco.pw.data.dao.contact;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.data.dao.common.ConnectionDB;



/**
 * A class to represent the DAO contact and its functions
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */

public class DAOContact extends ConnectionDB{
	
	public DAOContact(){}

	private Properties sql_properties = new Properties();

/**
 * Function that saves a given contact to the database Contacts
 *
 * @param contact Contact to add
 * @return integer value, it represents the status of the action
 *
 **/

	public int Save(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("Insert");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			ps.setString(2,contact.getName());
			ps.setString(3,contact.getSurname());
			ps.setDate(4,contact.getBirthday());
			ps.setString(5, contact.getPassword());
			
			status = ps.executeUpdate();
	
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	

/**
 * Function that updates a given contact in the database
 *
 * @param contact Contact to update
 * @return integer value, it represents the status of the action
 *
 **/
	public int Update(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
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



/**
 * Function that updates the password of a given contact in the database
 *
 * @param contact Contact which password is going to be updated
 * @return integer value, it represents the status of the action
 *
 **/

	public int UpdatePassword(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("UpdatePassword");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getPassword());
			ps.setString(2,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}


/**
 * Function that deletes a given contact from the database
 *
 * @param contact Contact to delete
 * @return integer value, it represents the status of the action
 *
 **/

	public int Delete(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("Delete");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}


/**
 * Function that shows all the contact in the database
 *
 * @return ArrayList <Contact> value, a list with all the contacts
 *
 **/
	
	public ArrayList <Contact> ListContacts(){

		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

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


/**
 * Function that queries a contact in the database from a given contact with the email to search for
 *
 * @param contact Contact to query
 * @return Contact, the contact with the full information
 *
 **/

	public Contact QueryByEmail (Contact contact){
		
		Statement stmt = null; 
		Contact resul = null;
		
		try {
			
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByEmail");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getEmail() + "'");
		    
		    while (rs.next()) {
		    	
		    	resul = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close();
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	} 

/**
 * Function that queries contacts in the database from a given contact with the name to search for
 *
 * @param contact Contact to query
 * @return ArrayList <Contact>, a list of contacts with the given name
 *
 **/

	public ArrayList <Contact> QueryByName (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByName");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getName() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
		    	
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
	
/**
 * Function that queries contacts in the database from a given contact with the surname to search for
 *
 * @param contact Contact to query
 * @return ArrayList <Contact>, a list of contacts with the given surname
 *
 **/

	public ArrayList <Contact> QueryBySurname (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryBySurname");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getSurname() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

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

/**
 * Function that queries contacts in the database from a given contact with the fullname to search for
 *
 * @param contact Contact to query
 * @return ArrayList <Contact>, a list of contacts with the given fullname
 *
 **/

	public ArrayList <Contact> QueryByFullname (Contact contact){
		
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null; 

		try {
			
			Connection con=getConnection();

			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryByFullname");
			
			PreparedStatement stmt = con.prepareStatement(statement);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getSurname());
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {

		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

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


/**
 * Function that queries contacts in the database from a given contact with the age to search for
 *
 * @param contact Contact to query
 * @return ArrayList <Contact>, a list of contacts with the given age
 *
 **/
	public ArrayList <Contact> QueryByAge (int Age){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
				aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
				
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
