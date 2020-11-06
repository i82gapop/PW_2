package es.uco.pw.data.dao.interest;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.pw.data.dao.common.ConnectionDB;
import es.uco.pw.business.contact.Contact;

public class DAOInterest extends ConnectionDB{

    public static int Save(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("InsertInterest");
            
            for (String string : contact.getInterests()) {

                PreparedStatement ps=con.prepareStatement(statement);
            
                ps.setString(1,contact.getEmail());
                ps.setString(2, string);
                
                status = ps.executeUpdate();
            }
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}


    public static ArrayList <String> ListInterests(){

		Statement stmt = null; 
		ArrayList <String> resul = new ArrayList <String>();

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("ListInterests");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
				resul.add(rs.getString("Interest"));
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
    }

    public static ArrayList <String> QueryInterestsByContact(Contact contact){

		Statement stmt = null; 
		ArrayList <String> resul = new ArrayList <String>();

		try {
			
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("QueryInterestByEmail");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement+ "'" + contact.getEmail() + "'");
		    
		    while (rs.next()) {
		    	
				resul.add(rs.getString("Interest"));
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
    }
    
    public static int Delete(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("DeleteInterests");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    
    public static ArrayList<Contact> QueryByInterests(ArrayList <String> interests){

        Statement stmt = null; 
		ArrayList <Contact> contacts = new ArrayList <Contact>();
        Contact resul = null;

		try{

            Connection con = getConnection();
            
            Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
            String statement = sql_properties.getProperty("QueryByInterest");
            
            for (String string : interests) {

                stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(statement + "'" + string + "'");
                
                while (rs.next()) {
		    	
                    resul = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"));
                    
                    if(contacts.isEmpty()){

                        contacts.add(resul);
                    }

                    else{

                        for (Contact contact : contacts) {
                        
                            if(contact.getEmail().equals(resul.getEmail()) == false ){
        
                                contacts.add(resul);
                            }
                        }
                    }
                }
               
                if (stmt != null) {
                    
                    stmt.close(); 
                }
            }

		}catch(Exception e){System.out.println(e);}

        return contacts;
	}
}
