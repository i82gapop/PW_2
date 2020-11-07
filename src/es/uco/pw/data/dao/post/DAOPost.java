package es.uco.pw.data.dao.post;

import java.io.FileInputStream;
import java.security.Principal;
import java.sql.*;
import java.util.Properties;

import es.uco.pw.business.post.*;
import es.uco.pw.data.dao.common.ConnectionDB;

public class DAOPost extends ConnectionDB{


	public static int Save(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
            sql_properties.load(sql_properties_file);
            
            if(post.getType().equals(Type.GENERAL)){

                General_Post aux_post = null;
                aux_post = (General_Post)post;
                String statement = sql_properties.getProperty("InsertGeneralPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,aux_post.getTypeString());
                ps.setString(2,"EDITED");
                ps.setString(3,aux_post.getTitle());
                ps.setString(4,aux_post.getBody());
                ps.setString(5, aux_post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("El post creado tendra asignada la ID: " + "<" + id+ ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                Individualized_Post aux_post = null;
                aux_post = (Individualized_Post)post;
                String statement = sql_properties.getProperty("InsertIndividualizedPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,aux_post.getTypeString());
                ps.setString(2,"EDITED");
                ps.setString(3,aux_post.getTitle());
                ps.setString(4,aux_post.getBody());
                ps.setString(5, aux_post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("El post creado tendra asignada la ID: " + "<" + id+ ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.THEMATIC)){
    
                Thematic_Post aux_post = null;
                aux_post = (Thematic_Post)post;
                String statement = sql_properties.getProperty("InsertThematicPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,aux_post.getTypeString());
                ps.setString(2,"EDITED");
                ps.setString(3,aux_post.getTitle());
                ps.setString(4,aux_post.getBody());
                ps.setString(5, aux_post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("El post creado tendra asignada la ID: " + "<" + id+ ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else{
    
                Flash_Post aux_post = null;
                aux_post = (Flash_Post)post;
                String statement = sql_properties.getProperty("InsertFlashPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,aux_post.getTypeString());
                ps.setString(2,"EDITED");
                ps.setString(3,aux_post.getTitle());
                ps.setString(4,aux_post.getBody());
                ps.setString(5, aux_post.getOwner().getEmail());
                ps.setDate(6, aux_post.getDateStart());
                ps.setDate(7, aux_post.getDateEnd());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("El post creado tendra asignada la ID: " + "<" + id+ ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }  

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }    
}
