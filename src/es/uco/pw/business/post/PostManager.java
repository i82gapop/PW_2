package es.uco.pw.business.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import es.uco.pw.business.contact.Contact;
//import es.uco.pw.business.contact.ContactManager;
import es.uco.pw.data.dao.contact.DAOContact;
import es.uco.pw.data.dao.interest.DAOInterest;
import es.uco.pw.data.dao.post.DAOPost;


public class PostManager {

    private static PostManager instance = null;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm/dd-MM-yyyy");
    private Scanner in = new Scanner (System.in);
    //private ContactManager contact_manager = ContactManager.getInstance();

    private PostManager(){}


    public static PostManager getInstance() throws ParseException {

        if(instance == null){

            instance = new PostManager();
        }

        return instance;
    }


    public void Menu() throws ParseException{

        Contact user = new Contact();
        int id;
        String buffer;

        System.out.println("ADVERTISEMENTS MANAGEMENT SYSTEM");
        System.out.println("Type your email to login in: ");
        buffer = in.next();
        user.setEmail(buffer);
        System.out.println("Type the password: ");
        buffer = in.next();
        user.setPassword(buffer);


        
        if((user = DAOContact.QueryByEmail(user)) != null){

            if(user.getPassword().equals(buffer)){
                
                String buff_title, buff_body;
                java.sql.Timestamp buff_date_start, buff_date_end;
                //ArrayList <String> buff_interests = new ArrayList<String>();
                Contact capsule = new Contact();

                int option = 1;

                while(option != 0){

                    in = new Scanner (System.in);
                    System.out.println("ADVERTISEMENTS MANAGEMENT SYSTEM");
                    System.out.println("OPTIONS");
                    System.out.println("=====================================");
                    System.out.println("=====================================");
                    System.out.println("1. Create a post");
                    System.out.println("2. Post a post");
                    System.out.println("3. Edit a post");
                    System.out.println("4. Archive a post");
                    System.out.println("5. Search a post");
                    System.out.println("6. Show board");
                    System.out.println("0. Exit the menu");
                    System.out.println("=====================================");
                    System.out.println("Your option: ");

                    option = in.nextInt();

                    Post aux_post;

                    switch(option){

                        case 0:
                        
                            System.out.println("All under control.");
                            System.out.println("Have a nice day, thank you for trusting on us.");
                            
                            break;
    
                        case 1:
                        
                            int sub_option;
                            
                            in = new Scanner (System.in);
                            System.out.println("What type of post do you want to create?: ");
                            System.out.println("1. General Post.");
                            System.out.println("2. Thematic Post.");
                            System.out.println("3. Individualized Post.");
                            System.out.println("4. Flash Post");
                            System.out.println("0. Cancel");
                            System.out.println("=====================================");
                            System.out.println("Your option: ");
                            sub_option = in.nextInt();
                                
                            switch(sub_option){
                                
                                case 0:
                                
                                    System.out.println("Cancelled");
                                    
                                    break;
                                    
                                case 1:

                                    in = new Scanner (System.in);
                                    System.out.println("Type the title of the post: ");
                                    buff_title = in.nextLine();
                                    

                                    System.out.println("Type the body of the post: ");
                                    buff_body = in.nextLine();

                                    aux_post = new Post(0, buff_title, buff_body, user);
                                    aux_post.setType(Type.GENERAL);

                                    DAOPost.Save(aux_post);

                                    System.out.println("Post created successfully.");

                                    break;

                                case 2:

                                    in = new Scanner (System.in);
                                    System.out.println("Type the title of the post: ");
                                    buff_title = in.nextLine();
                                    

                                    System.out.println("Type the body of the post: ");
                                    buff_body = in.nextLine();

                                    ArrayList <String> interests_list = DAOInterest.ListInterests();

                                    System.out.println("What interests does your post have from the following: " + interests_list);

                                    in = new Scanner (System.in);

                                    buffer = in.nextLine();
                                    StringTokenizer interests = new StringTokenizer(buffer.replace(" ", ""), ",");
                                    ArrayList <String> token_interests = new ArrayList <String>();

                                    while(interests.hasMoreTokens()){

                                        token_interests.add(interests.nextToken().toUpperCase());
                                    }

                                    for(int i = 0; i < token_interests.size(); i++){

                                        if(interests_list.contains(token_interests.get(i))){

                                            capsule.addInterest(token_interests.get(i));
                                        }
                                    }

                                    aux_post = new Post(0, buff_title, buff_body, user);
                                    aux_post.setInterests(capsule.getInterests());
                                    aux_post.setType(Type.THEMATIC);

                                    DAOPost.Save(aux_post);

                                    System.out.println("Post created successfully.");

                                    break;

                                case 3:

                                    ArrayList <String> buff_recipients = new ArrayList<String>();

                                    in = new Scanner (System.in);
                                    System.out.println("Type the title of the post: ");
                                    buff_title = in.nextLine();
                                    

                                    System.out.println("Type the body of the post: ");
                                    buff_body = in.nextLine();

                                    System.out.println("What recipients do you want the post to have: ");

                                    in = new Scanner (System.in);

                                    buffer = in.nextLine();
                                    StringTokenizer recipients = new StringTokenizer(buffer.replace(" ", ""), ",");
                                    ArrayList <String> token_recipients = new ArrayList <String>();

                                    while(recipients.hasMoreTokens()){

                                        token_recipients.add(recipients.nextToken());
                                    }

                                    for(int i = 0; i < token_recipients.size(); i++){

                                        capsule.setEmail(token_recipients.get(i));

                                        if(DAOContact.QueryByEmail(capsule)!=null){

                                            buff_recipients.add(token_recipients.get(i));
                                        }
                                    }

                                    aux_post = new Post(0, buff_title, buff_body, user);
                                    aux_post.setRecipients(buff_recipients);
                                    aux_post.setType(Type.INDIVIDUALIZED);

                                    DAOPost.Save(aux_post);

                                    System.out.println("Post created successfully.");

                                    break;

                                case 4:

                                    java.util.Date date = new java.util.Date();

                                    in = new Scanner (System.in);
                                    System.out.println("Type the title of the post: ");
                                    buff_title = in.nextLine();
                                    

                                    System.out.println("Type the body of the post: ");
                                    buff_body = in.nextLine();

                                    System.out.println("When do you want to post it?: (format: HH:mm/dd-MM-yyyy)");
                                    buffer = in.nextLine();
                                    date = format.parse(buffer); 
                                    buff_date_start = new java.sql.Timestamp(date.getTime());

                                    System.out.println("When do you want to archive it?: (format: HH:mm/dd-MM-yyyy)");
                                    buffer = in.nextLine();
                                    date = format.parse(buffer); 
                                    buff_date_end = new java.sql.Timestamp(date.getTime());

                                    aux_post = new Post(0, buff_title, buff_body, user);
                                    aux_post.setDate_start(buff_date_start);
                                    aux_post.setDate_end(buff_date_end);
                                    aux_post.setType(Type.FLASH);

                                    DAOPost.Save(aux_post);

                                    System.out.println("Post created successfully.");

                                    break;
                                
                            }//aqui termina el switch de crear posts

                            break;

                        case 2:

                            in = new Scanner (System.in);
                            System.out.println("Type the id of the post to post it: ");
                            id = in.nextInt();

                            aux_post = new Post();

                            aux_post.setIdentifier(id);

                            if(DAOPost.QueryByID(aux_post) != null) {

                                if(DAOPost.QueryByID(aux_post).getOwner().getEmail().equals(user.getEmail())){

                                    aux_post = DAOPost.QueryByID(aux_post);
                                    aux_post.setStatus(Status.POSTED);

                                    DAOPost.UpdateStatus(aux_post);
                                }

                                else{
                                    System.out.println("You are not the owner of this post. ");
                                }
                            }

                            else {
                                System.out.println("That ID post doesn't exist. ");
                            }
                                
                            break;
    
                        case 3:
                            
                            


                            break;
    
                        case 4:

                            in = new Scanner (System.in);
                                System.out.println("Type the id of the post to archive it: ");
                                id = in.nextInt();

                                aux_post = new Post();

                                aux_post.setIdentifier(id);

                                if(DAOPost.QueryByID(aux_post) != null) {

                                    if(DAOPost.QueryByID(aux_post).getOwner().getEmail().equals(user.getEmail())){

                                        aux_post = DAOPost.QueryByID(aux_post);
                                        aux_post.setStatus(Status.ARCHIVED);

                                        DAOPost.UpdateStatus(aux_post);
                                    }

                                    else{
                                        System.out.println("You are not the owner of this post. ");
                                    }
                                }

                                else {
                                    System.out.println("That ID post doesn't exist. ");
                                }
    
                            break;
    
                        case 5:

                            break;
    
                        case 6:
    
                            break;
    
                        default:

                            System.out.println("Not a valid option. Try again.");
                            
                            break;
                        
                    }      
                }
            }

            else{

                System.out.println("The password entered is incorrect.");
            }
        }

        else{

            System.out.println("The user entered doesn't exist in the database");
        }
    }
}
