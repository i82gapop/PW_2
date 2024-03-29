package es.uco.pw.business.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import es.uco.pw.business.contact.Contact;
import es.uco.pw.data.dao.contact.DAOContact;
import es.uco.pw.data.dao.interest.DAOInterest;
import es.uco.pw.data.dao.post.DAOPost;



/**
 * A class to represent manager for posts
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */

public class PostManager {

    private static PostManager instance = null;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm/dd-MM-yyyy");
    private Scanner in = new Scanner (System.in);
    private DAOContact daoContact = new DAOContact();
    private DAOInterest daoInterest = new DAOInterest();
    private DAOPost daoPost = new DAOPost();
    ArrayList <String> interests_list = daoInterest.ListInterests();

    private PostManager(){}


    public static PostManager getInstance() throws ParseException {

        if(instance == null){

            instance = new PostManager();
        }

        return instance;
    }


    /**
    * A visual menu of the sytem
    * @throws ParseException if the format of the dates aren't allowed
    *
    **/

    public void Menu() throws ParseException{

        Contact user = new Contact();
        int id;
        String buffer;

        System.out.println("Updating flash posts status before entering the program...");
        UpdateFlash();
        
        System.out.println("ADVERTISEMENTS MANAGEMENT SYSTEM");
        System.out.println("Type your email to login in: ");
        buffer = in.next();
        user.setEmail(buffer);
        System.out.println("Type the password: ");
        buffer = in.next();
        user.setPassword(buffer);


        
        if((user = daoContact.QueryByEmail(user)) != null){

            if(user.getPassword().equals(buffer)){
                
                String buff_title, buff_body;
                java.sql.Timestamp buff_date_start, buff_date_end;
                //ArrayList <String> buff_interests = new ArrayList<String>();

                int option = 1;

                while(option != 0){

                    Contact capsule = new Contact();
                    in = new Scanner (System.in);
                    System.out.println("\033[32mADVERTISEMENTS MANAGEMENT SYSTEM");
                    System.out.println("\033[32mOPTIONS");
                    System.out.println("\033[36m=====================================");
                    System.out.println("\033[36m=====================================\u001B[0m");
                    System.out.println("1. Create a post");
                    System.out.println("2. Post a post");
                    System.out.println("3. Edit a post");
                    System.out.println("4. Archive a post");
                    System.out.println("5. Search a post");
                    System.out.println("6. Show board");
                    System.out.println("0. Exit the menu");
                    System.out.println("\033[36m=====================================");
                    System.out.println("\033[31mYour option: \u001B[0m");

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

                                    daoPost.Save(aux_post);

                                    System.out.println("Post created successfully.");

                                    break;

                                case 2:

                                    in = new Scanner (System.in);
                                    System.out.println("Type the title of the post: ");
                                    buff_title = in.nextLine();
                                    

                                    System.out.println("Type the body of the post: ");
                                    buff_body = in.nextLine();


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

                                    daoPost.Save(aux_post);

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

                                        if(daoContact.QueryByEmail(capsule)!=null){

                                            buff_recipients.add(token_recipients.get(i));
                                        }
                                    }

                                    aux_post = new Post(0, buff_title, buff_body, user);
                                    aux_post.setRecipients(buff_recipients);
                                    aux_post.setType(Type.INDIVIDUALIZED);

                                    daoPost.Save(aux_post);

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

                                    daoPost.Save(aux_post);

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

                            if(daoPost.QueryByID(aux_post) != null) {

                                if(daoPost.QueryByID(aux_post).getOwner().getEmail().equals(user.getEmail())){

                                    aux_post = daoPost.QueryByID(aux_post);
                                    aux_post.setStatus(Status.POSTED);

                                    daoPost.UpdateStatus(aux_post);
                                }

                                else{
                                    System.out.println("You are not the owner of this post. ");
                                }
                            }

                            else {
                                System.out.println("That ID post doesn't exist. ");
                            }
                                
                            break;
    
                        case 3: // Ruben
                            in = new Scanner(System.in);
                            System.out.println("What post do you want to edit?: (type the id)");
                            id = in.nextInt();

                            aux_post = new Post();
                            aux_post.setIdentifier(id);
                            aux_post = daoPost.QueryByID(aux_post);

                            if(aux_post != null){
                                if(aux_post.getStatus() == Status.EDITED){
                                    if(aux_post.getOwner().getEmail().equals(user.getEmail())){
                                        int cont = 0;
                                        int sub_option_ex = 1;
                                            while(sub_option_ex != 0){
                                                if(cont == 0){
                                                    System.out.println("What info of the post do you want to edit?: ");
                                                    System.out.println("1. Title");
                                                    System.out.println("2. Body");

                                                    if(aux_post.getType() == Type.GENERAL){
                                                        System.out.println("0. Cancel");
                                                    }

                                                    else if(aux_post.getType() == Type.THEMATIC){
                                                        System.out.println("3. Interests");
                                                        System.out.println("0. Cancel");
                                                    }

                                                    else if(aux_post.getType() == Type.INDIVIDUALIZED){
                                                        System.out.println("3. Recipients");
                                                        System.out.println("0. Cancel");
                                                    }

                                                    else if(aux_post.getType() == Type.FLASH){
                                                        System.out.println("3. Publication date");
                                                        System.out.println("4. End date");
                                                        System.out.println("0. Cancel");
                                                    }
                                                }
                                                else{
                                                    System.out.println("What info of the post do you want to edit?: ");
                                                    System.out.println("1. Title");
                                                    System.out.println("2. Body");

                                                    if(aux_post.getType() == Type.GENERAL){
                                                        System.out.println("0. Save all changes");
                                                    }

                                                    else if(aux_post.getType() == Type.THEMATIC){
                                                        System.out.println("3. Interests");
                                                        System.out.println("0. Save all changes");
                                                    }

                                                    else if(aux_post.getType() == Type.INDIVIDUALIZED){
                                                        System.out.println("3. Recipients");
                                                        System.out.println("0. Save all changes");
                                                    }

                                                    else if(aux_post.getType() == Type.FLASH){
                                                        System.out.println("3. Publication date");
                                                        System.out.println("4. End date");
                                                        System.out.println("0. Save all changes");
                                                    }

                                                }
                                            
                                            System.out.println("=====================================");
                                            System.out.println("Your option: ");

                                            sub_option_ex = in.nextInt();


                                            if(sub_option_ex == 1){
                                                System.out.println("Type the new title of the post: ");
                                                buff_title = in.next();
                                                aux_post.setTitle(buff_title);
                                                cont++;
                                            }

                                            else if(sub_option_ex == 2){
                                                System.out.println("Type the new body of the post: ");
                                                buff_body = in.next();
                                                aux_post.setBody(buff_body);
                                                cont++;
                                            }

                                            else if(sub_option_ex == 3){
                                                if(aux_post.getType() == Type.THEMATIC){

                                                    System.out.println("Type the new interests of the post:" + interests_list);
                                                    in = new Scanner(System.in);

                                                    buffer = in.nextLine();

                                                    StringTokenizer interests_ = new StringTokenizer(buffer.replace(" ", ""), ",");
                                                    ArrayList <String> token_interests_ = new ArrayList <String>();

                                                    while(interests_.hasMoreTokens()){
                                                    
                                                        token_interests_.add(interests_.nextToken().toUpperCase());
                                                    }
                                                
                                                    for(int i = 0; i < token_interests_.size(); i++){
                                                    
                                                        if(interests_list.contains(token_interests_.get(i))){
                                                        
                                                            capsule.addInterest(token_interests_.get(i));
                                                        }
                                                    }
                                                
                                                    aux_post.setInterests(capsule.getInterests());     
                                                    cont++;                    

                                                }

                                                else if(aux_post.getType() == Type.INDIVIDUALIZED){

                                                    ArrayList <String> buff_recipients_ = new ArrayList<String>();

                                                    System.out.println("Type the new recipients of the post: ");
                                                    in = new Scanner(System.in);
                                                    buffer = in.nextLine();

                                                    StringTokenizer recipients_ = new StringTokenizer(buffer.replace(" ", ""), ",");
                                                    ArrayList <String> token_recipients_ = new ArrayList <String>();

                                                    while(recipients_.hasMoreTokens()){
                                                    
                                                        token_recipients_.add(recipients_.nextToken());
                                                    }
                                                
                                                    for(int i = 0; i < token_recipients_.size(); i++){
                                                    
                                                        capsule.setEmail(token_recipients_.get(i));
                                                    
                                                        if(daoContact.QueryByEmail(capsule)!=null){
                                                        
                                                            buff_recipients_.add(token_recipients_.get(i));
                                                        }
                                                    }
                                                
                                                    aux_post.setRecipients(buff_recipients_);
                                                
                                                
                                                    System.out.println("Recipients edited successfully.");
                                                    cont++;

                                                }


                                                else if(aux_post.getType() == Type.FLASH){

                                                    java.util.Date date_ = new java.util.Date();

                                                    System.out.println("Type the new start date of the post: (format: HH:mm/dd-MM-yyyy)");
                                                    in = new Scanner (System.in);
                                                    buffer = in.nextLine();
                                                    date_ = format.parse(buffer); 
                                                    buff_date_start = new java.sql.Timestamp(date_.getTime());
                                                    aux_post.setDate_start(buff_date_start);
                                                    System.out.println("Start date edited successfully.");
                                                    cont++;

                                                }
                                            }

                                            else if(sub_option_ex == 4){

                                                if(aux_post.getType() == Type.FLASH){

                                                    java.util.Date aux_date = new java.util.Date();

                                                    System.out.println("Type the new archive date of the post: (format: HH:mm/dd-MM-yyyy)");
                                                    in = new Scanner (System.in);
                                                    buffer = in.nextLine();
                                                    aux_date = format.parse(buffer); 
                                                    buff_date_end = new java.sql.Timestamp(aux_date.getTime());
                                                    aux_post.setDate_end(buff_date_end);
                                                    System.out.println("Archive date edited successfully.");
                                                    cont++;
                                                }

                                                else{
                                                    System.out.println("Not a valid option. ");
                                                }
                                            }

                                            else if (sub_option_ex == 0){
                                                if(cont == 0){
                                                    System.out.println("Cancelled.");
                                                }
                                                else{
                                                daoPost.Update(aux_post);
                                                System.out.println("All changes saved in the Database.");
                                                }
                                            }

                                            else{
                                                System.out.println("Not a valid option. ");
                                            }
                                        }
                                    }

                                    else{
                                        System.out.println("You are not the owner of this post. ");
                                    }
                                }
                                else{
                                    System.out.println("The post isn't in an edited Status so can't be edited. ");
                                }
                            }
                            else{
                                System.out.println("That ID post doesn't exist. ");
                            }


                            break;
    
                        case 4:

                            in = new Scanner (System.in);
                                System.out.println("Type the id of the post to archive it: ");
                                id = in.nextInt();

                                aux_post = new Post();

                                aux_post.setIdentifier(id);

                                if(daoPost.QueryByID(aux_post) != null) {

                                    if(daoPost.QueryByID(aux_post).getOwner().getEmail().equals(user.getEmail())){

                                        aux_post = daoPost.QueryByID(aux_post);
                                        aux_post.setStatus(Status.ARCHIVED);

                                        daoPost.UpdateStatus(aux_post);
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

                            ConsultPost();

                            break;
    
                        case 6:
                            
                            ArrayList <Post> res  = new ArrayList<Post>();

                            in = new Scanner (System.in);

                            System.out.println("How you want to order the posts by?");
                            System.out.println("1. By owner");
                            System.out.println("2. By date");
                            System.out.println("0. Cancel");
                            System.out.println("=====================================");
                            System.out.println("Your option: ");
                                
                            int sub_option_ex;
                            sub_option_ex = in.nextInt();
                            
                            if(sub_option_ex == 1){

                                res = daoPost.OrderByOwner();
                                
                                System.out.println("==========================================================================");
                                System.out.println("Showing the board for " + user.getEmail());
                                System.out.println("==========================================================================");
                                System.out.println("General Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.GENERAL)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Individualized Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.INDIVIDUALIZED)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Thematic Posts: ");
                                System.out.println("==========================================================================");

                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.THEMATIC)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Flash Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.FLASH)){

                                        System.out.println(post.toString());
                                    }
                                }
                            }

                            else if(sub_option_ex == 2){
                                
                                res = daoPost.OrderByDate();

                                System.out.println("==========================================================================");
                                System.out.println("Showing the board for " + user.getEmail());
                                System.out.println("==========================================================================");
                                System.out.println("General Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.GENERAL)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Individualized Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.INDIVIDUALIZED)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Thematic Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.THEMATIC)){

                                        System.out.println(post.toString());
                                    }
                                }

                                System.out.println("==========================================================================");
                                System.out.println("Flash Posts: ");
                                System.out.println("==========================================================================");


                                for (Post post : res) {
                                    
                                    if(post.getType().equals(Type.FLASH)){

                                        System.out.println(post.toString());
                                    }
                                }
                            }

                            else{

                                System.out.println("Not a valid option. Try again.");
                            }
    
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

    /**
    * A visual menu to see Consult options
    *
    **/

    public void ConsultPost() throws ParseException{
        
        int option;

        ArrayList <Post> res  = new ArrayList<Post>();
        Post aux_post = null;
        String search_term;
        java.sql.Timestamp publication;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println("=====================================");
        System.out.println("Post's Search");
        System.out.println("=====================================");
        System.out.println("How do you wish to search for your post?: ");
        System.out.println("1. By date");
        System.out.println("2. By interest/theme");
        System.out.println("3. By owner");
        System.out.println("4. By recipient");
        System.out.println("0. Exit the menu");
        System.out.println("=====================================");
        System.out.println("Your option: ");
        option = in.nextInt();

            if(option == 1){

                in = new Scanner (System.in);
                aux_post = new Post();

                java.util.Date date = new java.util.Date();

                System.out.println("Type the date of the posts to search: (dd-MM-yyyy)");
                search_term = in.nextLine();
                
                date = format.parse(search_term); 
                publication = new java.sql.Timestamp(date.getTime());

                aux_post.setPublication(publication);

                if((res = daoPost.QueryByDate(aux_post)) != null){

                    System.out.println("Showing the result of the search: ");
                    
                    for (Post post : res) {
                        
                        System.out.println(post.toString());
                    }
                }

                else{

                    System.out.println("No results.");
                }
            }

            else if(option == 2){

                in = new Scanner (System.in);
                aux_post = new Post();
                Contact capsule = new Contact();

                System.out.println("What interests does your post have from the following: " + interests_list);
                in = new Scanner (System.in);
                search_term = in.nextLine();

                StringTokenizer interests = new StringTokenizer(search_term.replace(" ", ""), ",");
                ArrayList <String> token_interests = new ArrayList <String>();

                while(interests.hasMoreTokens()){

                    token_interests.add(interests.nextToken().toUpperCase());
                }

                for(int i = 0; i < token_interests.size(); i++){

                    if(interests_list.contains(token_interests.get(i))){

                        capsule.addInterest(token_interests.get(i));
                    }
                }

                aux_post.setInterests(capsule.getInterests());


                if((res = daoPost.QueryByInterests(aux_post)) != null){

                    System.out.println("Showing the results of the search: ");

                    for(int i = 0; i < res.size(); i++){

                        System.out.println(res.get(i).toString());
                    }
                }

                else{

                    System.out.println("No results.");
                }
            }

            else if(option == 3){

                in = new Scanner (System.in);
                aux_post = new Post();
                
                System.out.println("Type the owner's email of the posts to search: ");
                search_term = in.next();

                Contact capsule = new Contact();
                capsule.setEmail(search_term);

                aux_post.setOwner(daoContact.QueryByEmail(capsule));
                
                if((res = daoPost.QueryByOwner(aux_post)) != null){

                    System.out.println("Showing the results of the search: ");

                    for(int i = 0; i < res.size(); i++){

                        System.out.println(res.get(i).toString());
                    }
                }

                else{

                    System.out.println("No results.");
                }
            }

            else if (option == 4){//probar

                in = new Scanner (System.in);
                aux_post = new Post();
                
                System.out.println("Type the email of the recipient of the posts to search: ");
                search_term = in.next();

                ArrayList <String> recipients = new ArrayList <String>();
                recipients.add(search_term);
                
                aux_post.setRecipients(recipients);

                if((res = daoPost.QueryByRecipient(aux_post)) != null){

                    System.out.println("Showing the results of the search: ");

                    for(int i = 0; i < res.size(); i++){

                        System.out.println(res.get(i).toString());
                    }
                }

                else{

                    System.out.println("No results.");
                }
            }

            else if(option == 0){

                System.out.println("Exiting the menu");
            }

            else{

                System.out.println("Wrong option. Try using a valid option next time (between 0-4).");
            }
    }

    public void UpdateFlash(){


        java.sql.Timestamp system_time= new java.sql.Timestamp(System.currentTimeMillis());

        Post aux_post = new Post();

        aux_post.setPublication(system_time);

        daoPost.UpdateFlashStart(aux_post);
        daoPost.UpdateFlashEnd(aux_post);
    }   
}
