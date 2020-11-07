package es.uco.pw.business.post;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import es.uco.pw.business.contact.*;
import es.uco.pw.data.dao.contact.DAOContact;
import es.uco.pw.data.dao.post.DAOPost;


public class PostManager {

    private static PostManager instance = null;
    private Scanner in = new Scanner (System.in);
    private ContactManager contact_manager = ContactManager.getInstance();


    private PostManager(){}


    public static PostManager getInstance() throws ParseException {

        if(instance == null){

            instance = new PostManager();
        }

        return instance;
    }


    public void Menu() throws ParseException{

        Contact aux_contact = new Contact();
        String buffer = in.next();

        System.out.println("ADVERTISEMENTS MANAGEMENT SYSTEM");
        System.out.println("Type your email to login in: ");
        buffer = in.next();
        aux_contact.setEmail(buffer);
        System.out.println("Type the password: ");
        buffer = in.next();
        aux_contact.setPassword(buffer);


        
        if((aux_contact = DAOContact.QueryByEmail(aux_contact)) != null){

            if(aux_contact.getPassword().equals(buffer)){
                
                String buff_title, buff_body, log_username, buff_interest, buff_recipients, buff_date_start, buff_date_end;

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

                    
                    Post_Creator_Board aux_post_creator = new Post_Creator_Board();
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

                                    System.out.println("Post created successfully.");

                                    aux_post = aux_post_creator.getPost(0, Type.GENERAL, buff_title, buff_body, aux_contact, null, null, null, null);

                                    DAOPost.SaveGeneral(aux_post);

                                    
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                
                            }
                            break;

                        case 2:
                            
                            break;
    
                        case 3:
                            
                            break;
    
                        case 4:
    
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
