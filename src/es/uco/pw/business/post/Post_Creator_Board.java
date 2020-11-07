package es.uco.pw.business.post;

import java.sql.Date;
import java.util.ArrayList;
import es.uco.pw.business.contact.Contact;

    /**
     * The concrete factory that creates posts by their type
     * @author Pedro Pablo Garcia Pozo
     * @author Ruben Borrego Canovaca
     * @since 26-09-2020
     * @version 2.0
     * */
    
    
    public class Post_Creator_Board implements Post_Creator{

    public Post getPost(int id, Type type, String title, String body, Contact owner, Date date_start, Date date_end, ArrayList <String> recipients, ArrayList <String> interests){

        if(type == Type.GENERAL){

            return new General_Post(id, title, body, owner);
        }

        else if(type == Type.INDIVIDUALIZED){

            return new Individualized_Post(id, title, body, owner, recipients);
        }

        else if(type == Type.THEMATIC){

            return new Thematic_Post(id, title, body, owner, interests);
        }

        else if(type == Type.FLASH){

            return new Flash_Post(id, title, body, owner, date_start, date_end);
        }

        else{

            return null;
        }
    }
}
