package es.uco.pw.business.contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import es.uco.pw.data.dao.contact.DAOContact;
import es.uco.pw.data.dao.interest.DAOInterest;


/**
 * A class to represent manager for contacts
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */





public class ContactManager {

    
    // Singleton declaration
    private static ContactManager instance = null;
    private Scanner in = new Scanner (System.in);

    // private constructor

    private ContactManager(){}

    // Access point to the instance

    public static ContactManager getInstance(){

        if(instance == null){

            instance = new ContactManager();
        }

        return instance;
    }

    /**
    * A visual menu of the sytem
    * @throws ParseException if the format of the dates aren't allowed
    *
    **/

    public void Menu() throws ParseException{

        int option = 1;
        String buffer;
        Contact aux_contact = new Contact();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        ArrayList <Contact> contacts = new ArrayList <Contact>();
        java.util.Date date = new java.util.Date();
        java.sql.Date sql_date = null;

        while(option != 0){
        System.out.println("CONTACT MANAGEMENT SYSTEM");
        System.out.println("=====================================");
        System.out.println("OPTIONS");
        System.out.println("=====================================");
        System.out.println("1. Add contact");
        System.out.println("2. Eliminate contact");
        System.out.println("3. Update contact");
        System.out.println("4. Consult contacts");
        System.out.println("5. Show all contacts");
        System.out.println("6. Change the password of a contact");
        System.out.println("0. Exit the menu");
        System.out.println("=====================================");
        System.out.println("Your option: ");

        option = in.nextInt();

            switch(option){

                case 0:

                    System.out.println("Saving all changes in the file.............");
                    System.out.println("All under control.");
                    System.out.println("Have a nice day, thank you for trusting on us");

                    break;

                case 1:

                    in = new Scanner (System.in);
                    System.out.println("Type the name of the contact below:");
                    buffer = in.nextLine();
                    aux_contact.setName(buffer);

                    System.out.println("Type the surname of the contact below:");
                    buffer = in.nextLine();
                    aux_contact.setSurname(buffer);

                    System.out.println("Type the Birthday of the contact below: (format: dd-MM-yyyy)");
                    buffer = in.nextLine();
		            date = format.parse(buffer); 
		            sql_date = new java.sql.Date(date.getTime());
                    aux_contact.setBirthday(sql_date);

                    System.out.println("Type the email of the contact below:");
                    buffer = in.next();
                    aux_contact.setEmail(buffer);

                    System.out.println("Type the password of the contact below:");
                    buffer = in.next();
                    aux_contact.setPassword(buffer);

                    if(!checkExistence(aux_contact)){

                        ArrayList <String> interests_list = DAOInterest.ListInterests();

                        System.out.println("What interests does your contact have from the following: " + interests_list);

                        in = new Scanner (System.in);

                        buffer = in.nextLine();
                        StringTokenizer interests = new StringTokenizer(buffer.replace(" ", ""), ",");
                        ArrayList <String> token_interests = new ArrayList <String>();

                        while(interests.hasMoreTokens()){

                            token_interests.add(interests.nextToken().toUpperCase());
                        }

                        for(int i = 0; i < token_interests.size(); i++){

                            if(interests_list.contains(token_interests.get(i))){

                                aux_contact.addInterest(token_interests.get(i));
                            }
                        }

                        DAOContact.Save(aux_contact);
                        DAOInterest.Save(aux_contact);
                    }

                    else {

                        System.out.println("Email already on the database.");
                    }

                    break;

                case 2:

                    if(isEmpty()){

                        System.out.println("No contacts to delete.");
                    }

                    else{

                        in = new Scanner (System.in);
                        System.out.println("Type the email of the contact to remove below: ");
                        buffer = in.next();
                        aux_contact.setEmail(buffer);

                        System.out.println("Type the password of the contact: ");
                        buffer = in.next();

                        if(!checkExistence(aux_contact)){

                            System.out.println("There's no email like this in the database");
                        }

                        else{

                            if(DAOContact.QueryByEmail(aux_contact).getPassword().equals(buffer)){
                                
                                DAOContact.Delete(aux_contact);
                                DAOInterest.Delete(aux_contact);

                            }

                            else{
                                System.out.println("Incorrect password.");
                            }
                        }
                    }

                    break;

                case 3:

                    if(isEmpty()){

                        System.out.println("No contacts to update.");
                    }

                    else{

                        in = new Scanner (System.in);
                        System.out.println("Type the email of the contact to update below: ");
                        buffer = in.next();
                        aux_contact.setEmail(buffer);

                        if(!checkExistence(aux_contact)){

                            System.out.println("There's no email like this in the database");
                        }
                        

                        else{

                            System.out.println("Type the password of the contact: ");
                            buffer = in.next();
                            if(DAOContact.QueryByEmail(aux_contact).getPassword().equals(buffer)){

                            
                            try{

                                in = new Scanner (System.in);
                                System.out.println("Lets add the new information of the contact.");
                                System.out.println("Type the new name of the contact below:");
                                buffer = in.nextLine();
                                aux_contact.setName(buffer);

                                System.out.println("Type the new surname of the contact below:");
                                buffer = in.nextLine();
                                aux_contact.setSurname(buffer);

                                System.out.println("Type the new Birthday of the contact below: (format: dd-MM-yyyy)");
                                buffer = in.nextLine();
                                date = format.parse(buffer); 
                                sql_date = new java.sql.Date(date.getTime());
                                aux_contact.setBirthday(sql_date);


                                ArrayList <String> interests_list = DAOInterest.ListInterests();

                                System.out.println("What new interests does your contact have from the following: " + interests_list);

                                in = new Scanner (System.in);

                                buffer = in.nextLine();
                                StringTokenizer interests = new StringTokenizer(buffer.replace(" ", ""), ",");
                                ArrayList <String> token_interests = new ArrayList <String>();


                                while(interests.hasMoreTokens()){

                                    token_interests.add(interests.nextToken().toUpperCase());
                                }
        
                                for(int i = 0; i < token_interests.size(); i++){
        
                                    if(interests_list.contains(token_interests.get(i))){
        
                                        aux_contact.addInterest(token_interests.get(i));
                                    }
                                }

                                DAOInterest.Delete(aux_contact);
                                DAOContact.Update(aux_contact);
                                DAOInterest.Save(aux_contact);

                            }catch(ParseException e){
                                    System.out.println("Not a valid format for the date, try with this format: dd/MM/yyyy.");
                                    }

                            }else{
                                System.out.println("Incorrect password.");
                            }
                        }
                    }


                    break;

                case 4:
                    
                    ConsultContact();

                    break;
                    
                case 5:
                    
                    if(isEmpty()){

                        System.out.println("No contacts in the database");                        
                    }

                    else{

                        contacts = DAOContact.ListContacts();

                        System.out.println("---------------------------Contacts---------------------------");        

                        for (Contact contact : contacts) {
                            
                            contact.setInterest(DAOInterest.QueryInterestsByContact(contact));
                            System.out.println(contact.toString());    
                        }

                        System.out.println("--------------------------------------------------------------");        
                    }

                    break;


                case 6:
                    in = new Scanner (System.in);
                    System.out.println("Type the email of the contact: ");
                    buffer = in.next();
                    aux_contact.setEmail(buffer);

                    if(!checkExistence(aux_contact)){

                        System.out.println("There's no email like this in the database");
                    }


                    else{

                        System.out.println("Type the old password of the contact: ");
                        buffer = in.next();
                        if(DAOContact.QueryByEmail(aux_contact).getPassword().equals(buffer)){

                            System.out.println("Type the new password of the contact: ");
                            buffer = in.next();
                            aux_contact.setPassword(buffer);

                            DAOContact.UpdatePassword(aux_contact);

                            System.out.println("Password changed successfully.");

                            }

                            else{
                                System.out.println("Incorrect password.");
                            }
                        }


                        break;

                default:
                    System.out.println("Not a valid option. Try again. [0-6]");
            }

        }

    }

    /**
    * Function that check the existence of a contact with a given email
    * 
    * @param contact Contact for searching of
    * @return boolean value, true if it exists; false if it doesn't
    *
    **/

    public boolean checkExistence(Contact contact){

        if(isEmpty()){

            return false;
        }
        
        else{

            if(DAOContact.QueryByEmail(contact) == null){

                return false;
            }
    
            else{
    
                return true;
            }
        }
    }



    /**
    * Function that check if the contact database is empty
    * 
    * @return boolean value, true if it is; false if it isn't
    *
    **/

    public boolean isEmpty(){

        if(DAOContact.ListContacts() == null){

            return true;
        }

        return false;
    }

    /**
    * A visual menu to see Consult options
    *
    **/

    public void ConsultContact(){
        
        int option;
        Contact aux_contact = new Contact();
        Contact single = new Contact();
        ArrayList <Contact> compound = new ArrayList <Contact>();
        String search_term;
        int search_term_int;

        System.out.println("=====================================");
        System.out.println("Contact's Consult");
        System.out.println("=====================================");
        System.out.println("How do you wish to search for your contact?: ");
        System.out.println("1. By email");
        System.out.println("2. By name (name and surname)");
        System.out.println("3. By interests");
        System.out.println("4. By age");
        System.out.println("0. Exit the menu");
        System.out.println("=====================================");
        System.out.println("Your option: ");
        
        option = in.nextInt();

            if(option == 1){

                System.out.println("Type the email of the contact to search: ");
                search_term = in.next();
                aux_contact.setEmail(search_term);

                if((single = DAOContact.QueryByEmail(aux_contact)) != null){

                    System.out.println("Showing the result of the search: ");
                    System.out.println("---------------------------Result---------------------------");
                    single.setInterest(DAOInterest.QueryInterestsByContact(single));    
                    System.out.println(single.toString());
                    System.out.println("--------------------------------------------------------------");       

                }

                else{

                    System.out.println("No results.");
                }
            }

            else if(option == 2){

                System.out.println("Type the name of the contact to search: ");

                in = new Scanner (System.in);
                search_term = in.nextLine();
                aux_contact.setName(search_term);

                System.out.println("Type the surname of the contact to search: ");

                in = new Scanner (System.in);
                search_term = in.nextLine();
                aux_contact.setSurname(search_term);

                if((compound = DAOContact.QueryByFullname(aux_contact)) != null){

                    System.out.println("Showing the results of the search: ");
                    System.out.println("---------------------------Results---------------------------");    
                    for(int i = 0; i < compound.size(); i++){

                        compound.get(i).setInterest(DAOInterest.QueryInterestsByContact(compound.get(i)));   
                        System.out.println(compound.get(i).toString());
                    }
                    System.out.println("--------------------------------------------------------------");    
                }

                else{

                    System.out.println("No results.");
                }
            }

            else if(option == 3){

                ArrayList <String> interests_list = DAOInterest.ListInterests();

                System.out.println("Type the interest of the contact to search: " + interests_list);
                in = new Scanner (System.in);
                search_term = in.nextLine();

                StringTokenizer interests = new StringTokenizer(search_term.replace(" ", ""), ",");
                ArrayList <String> token_interests = new ArrayList <String>();

                while(interests.hasMoreTokens()){

                    token_interests.add(interests.nextToken().toUpperCase());
                }

                for(int i = 0; i < token_interests.size(); i++){

                    if(interests_list.contains(token_interests.get(i))){

                        aux_contact.addInterest(token_interests.get(i));
                    }
                }

                token_interests = aux_contact.getInterests();

                if((compound = DAOInterest.QueryByInterests(token_interests)) != null){

                    System.out.println("Showing the results of the search: ");
                    System.out.println("---------------------------Results---------------------------");    
                    for(int i = 0; i < compound.size(); i++){

                        compound.get(i).setInterest(DAOInterest.QueryInterestsByContact(compound.get(i)));  
                        System.out.println(compound.get(i).toString());
                    }
                    System.out.println("--------------------------------------------------------------");  
                }

                else{

                    System.out.println("No results.");
                }
            }
            
            else if (option == 4){

                System.out.println("Type the age of the contact to search: ");
                search_term_int = in.nextInt();


                if((compound = DAOContact.QueryByAge(search_term_int)) != null){

                    System.out.println("Showing the results of the search: ");
                    System.out.println("---------------------------Results---------------------------");    
                    for(int i = 0; i < compound.size(); i++){

                        compound.get(i).setInterest(DAOInterest.QueryInterestsByContact(compound.get(i)));  
                        System.out.println(compound.get(i).toString());
                    }
                    System.out.println("--------------------------------------------------------------");
                }

                else{

                    System.out.println("No results.");
                }
            }
            
            else{

                System.out.println("Wrong option. Try using a valid option (between 0-4)");
                option = in.nextInt();
            }
        
    }
}
