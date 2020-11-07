package es.uco.pw.business.post;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import es.uco.pw.business.contact.Contact;

/**
 * The DTO Post of the database
 * @author Ruben Borrego Canovaca
 * @author Pedro Pablo Garcia Pozo
 * @since 4-11-2020
 * @version 2.0
 * */


public class Post {

    private int identifier;
    private Type type;
    private Status status;
    private String title;
    private String body;
    private Contact owner;
    private Date publication;
    private Date date_start;
    private Date date_end;
    private ArrayList <String> recipients = new ArrayList <String>();
    private ArrayList <String> interests = new ArrayList <String>();

    public int getIdentifier() {return identifier;}

    public Type getType() {return type;}

    public Status getStatus() {return status;}

    public String getTitle(){return title;}

    public String getBody(){return body;}

    public Contact getOwner(){return owner;}

    public Date getPublication(){return publication;}

    public Date getDate_start(){return date_start;}

    public Date getDate_end(){return date_end;}

    public ArrayList<String> getRecipients(){return recipients;}

    public ArrayList<String> getInterests(){return interests;}

    public void setIdentifier(int identifier) {this.identifier = identifier;}

    public void setType(Type type) {this.type = type;}

    public void setStatus(Status status) {this.status = status;}

    public void setTitle(String title) {this.title = title;}

    public void setBody(String body) {this.body = body;}

    public void setOwner(Contact owner) {this.owner = owner;}

    public void setPublication(Date publication) {this.publication = publication;}

    public void setDate_start(Date date_start) {this.date_start = date_start;}

    public void setDate_end(Date date_end) {this.date_end = date_end;}

    public void setRecipients(ArrayList<String> recipients) {this.recipients = recipients;}

    public void setInterests(ArrayList<String> interests) {this.interests = interests;}

    public String toString(){

        if(this.type == Type.GENERAL){

            return "Post {ID: " + identifier + "; Title: " + title + "; Body: " + body + "; Owner: " + owner.getEmail() + "; Publication: " + publication + "}";
        }

        else if(this.type == Type.INDIVIDUALIZED){

            return "Post {ID: " + identifier + "; Title: " + title + "; Body: " + body + "; Owner: " + owner.getEmail() + "; Publication: " + publication + "; Recipients: " +  recipients + "}";
        }

        else if(this.type == Type.THEMATIC){

            return "Post {ID: " + identifier + "; Title: " + title + "; Body: " + body + "; Owner: " + owner.getEmail() + "; Publication: " + publication + "; Interests: " +  recipients + "}";
        }

        else if(this.type == Type.FLASH){

            return "Post {ID: " + identifier + "; Title: " + title + "; Body: " + body + "; Owner: " + owner.getEmail() + "; Publication: " + publication + "; Starting Date: " + date_start + "; Ending Date: " + date_end + "}";
        }

        else{

            return "ERROR IN toString()";
        }
    }
}