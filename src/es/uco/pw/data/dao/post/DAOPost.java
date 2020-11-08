package es.uco.pw.data.dao.post;

import java.io.FileInputStream;
import java.security.Principal;
import java.sql.*;
import java.util.Properties;

import es.uco.pw.business.contact.Contact;
import es.uco.pw.business.post.*;
import es.uco.pw.data.dao.common.ConnectionDB;
import es.uco.pw.data.dao.contact.DAOContact;

public class DAOPost extends ConnectionDB{


	public static int Save(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
            sql_properties.load(sql_properties_file);
            
            if(post.getType().equals(Type.GENERAL)){

                String statement = sql_properties.getProperty("InsertGeneralPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,"GENERAL");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");                     
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                String statement = sql_properties.getProperty("InsertIndividualizedPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,"INDIVIDUALIZED");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                        post.setIdentifier(id);
                        SaveBis(post);
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.THEMATIC)){
    
                String statement = sql_properties.getProperty("InsertThematicPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,"THEMATIC");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                        post.setIdentifier(id);
                        SaveBis(post);
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else{
                
                String statement = sql_properties.getProperty("InsertFlashPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,"FLASH");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());
                ps.setTimestamp(6, post.getDate_start());
                ps.setTimestamp(7, post.getDate_end());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }  

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }    

    public static int SaveBis(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
            sql_properties.load(sql_properties_file);
    
            if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                String statement = sql_properties.getProperty("InsertPostRecipients");

                PreparedStatement ps=con.prepareStatement(statement);

                for (String string : post.getRecipients()) {
                    
                    ps.setString(1, string);
                    ps.setInt(2, post.getIdentifier());
    
                    status = ps.executeUpdate();
                }
            }
    
            else{
    
                String statement = sql_properties.getProperty("InsertPostInterests");

                PreparedStatement ps=con.prepareStatement(statement);
                
                for (String string : post.getInterests()) {
                    
                    ps.setString(1, string);
                    ps.setInt(2, post.getIdentifier());
    
                    status = ps.executeUpdate();
                }
            }

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }

    public static int UpdateStatus(Post post){//poner mensajitos de retroalimentacion
		
        int status=0;
		
		try{
			Connection con=getConnection();
			
			Properties sql_properties = new Properties();
			FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);
			String statement = sql_properties.getProperty("UpdateStatus");

            Status post_status = QueryByID(post).getStatus();

            if(!post_status.equals(Status.ARCHIVED)){//no esta archivado por lo que si se puede cambiar el estado

                if(post.getType().equals(Type.FLASH)){//si es flash

                    if(post.getStatus().equals(Status.POSTED)){//si quieres postearlo
    
                        if(post.getDate_start().after(new Timestamp(System.currentTimeMillis()))){//si todavia no ha llegado la fecha
    
                            PreparedStatement ps=con.prepareStatement(statement);
                            ps.setString(1,Status.WAITING.name());
                            ps.setInt(2,post.getIdentifier());
    
                            status=ps.executeUpdate();
                        }
    
                        else{//si ha llegado la fecha
    
                            statement = sql_properties.getProperty("UpdateStatusPublication");
    
                            PreparedStatement ps=con.prepareStatement(statement);
                            ps.setString(1,post.getStatus().name());
                            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                            ps.setInt(3,post.getIdentifier());
    
                            status=ps.executeUpdate();
                        }
    
                    }
    
                    else{//si no quieres postearlo
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setInt(2,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }
                }
    
                else{//si no es flash
    
                    if(post.getStatus().equals(Status.POSTED)){//si quieres postearlo
    
                        statement = sql_properties.getProperty("UpdateStatusPublication");
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        ps.setInt(3,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }
    
                    else{//si no quieres postearlo
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setInt(2,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }   
                }
            }

            else{

                System.out.println("You cant change the status of the post because its already archived.");
            }            

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    
    
    
    public static Post QueryByID(Post post){
        
        Statement stmt = null;
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();
            
            Properties sql_properties = new Properties();
            FileInputStream sql_properties_file = new FileInputStream("sql.properties");
            sql_properties.load(sql_properties_file);
            String statement = sql_properties.getProperty("QueryByID");
            
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + post.getIdentifier());
            
            while (rs.next()) {

                capsule.setEmail(rs.getString("Owner"));
                
                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), DAOContact.QueryByEmail(capsule));

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));
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
