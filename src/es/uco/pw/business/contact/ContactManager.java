package es.uco.pw.business.contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import es.uco.pw.data.dao.contact.DAOContact;

public class ContactManager {

    //aqui hacer el menu y no olvidar realizar un singleton (done)

    //funcion de insercion (falta por arreglar el tema de la sql date)
    //funcion de borrado (done)
    //funcion de actualizacion 
    //funcion de filtrado
    //funcion de imprimir contactos
    
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
/*
    public void Menu() throws ParseException{

        int option = 1;
        String buffer;
        Contact aux_contact = new Contact();

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
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    System.out.println("Type the name of the contact below:");
                    buffer = in.nextLine();
                    aux_contact.setName(buffer);

                    System.out.println("Type the surname of the contact below:");
                    buffer = in.nextLine();
                    aux_contact.setSurname(buffer);

                    System.out.println("Type the Birthday of the contact below: (format: dd-MM-yyyy)");
                    buffer = in.nextLine();
                    java.sql.Date date = format.parse(buffer);

                    aux_contact.setBirthday(format.parse(buffer));

                    System.out.println("Type the email of the contact below:");
                    buffer = in.next();
                    aux_contact.setEmail(buffer);

                    if(checkExistence(aux_contact) == false){

                        ArrayList <String> interests_list = DAOContact.ListInterests();

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
                    }

                    else {

                        System.out.println("Email already on the System.");
                    }

                    break;

                case 2:

                    in = new Scanner (System.in);
                    System.out.println("Type the email of the contacmpt to remove below: ");
                    buffer = in.next();
                    DAOContact.Delete(DAOContact.QueryByEmail(buffer));

                    break;

                case 3:

                if(contacts.size() == 0){

                    System.out.println("No contacts to update.");
                    break;
                }

                else{

                    try{

                        in = new Scanner (System.in);
                            System.out.println("Type the email of the contact to update below: ");
                        buff_email = in.next();

                        in = new Scanner (System.in);

                            System.out.println("Lets add the new information of the contact.");
                            System.out.println("Type the new name of the contact below:");
                        buff_name = in.nextLine();

                            System.out.println("Type the new surname of the contact below:");
                        buff_surname = in.nextLine();

                            System.out.println("Type the new Birthday of the contact below: (format: dd-MM-yyyy)");
                        buff_birth = in.next();

                        aux_contact = new Contact(buff_name, buff_surname, format.parse(buff_birth), buff_email);

                        System.out.println("What new interests does your contact have from the following: " + interests);

                        in = new Scanner (System.in);

                        String buff_interest_ = in.nextLine();
                        String without_spaces_ = buff_interest_.replace(" ", "");
                        StringTokenizer token_interest_ = new StringTokenizer(without_spaces_, ",");
                        ArrayList <String> token_interests_ = new ArrayList <String>();


                        while(token_interest_.hasMoreTokens()){

                            token_interests_.add(token_interest_.nextToken().toUpperCase());
                        }

                        for(int i = 0; i < token_interests_.size(); i++){

                            if(interests.contains(token_interests_.get(i))){

                                aux_contact.addInterest(token_interests_.get(i));
                            }
                        }

                        UpdateContact(aux_contact, SearchContactByEmail(buff_email));

                        }catch(ParseException e){
                                System.out.println("Not a valid format for the date, try with this format: dd/MM/yyyy.");
                                }

                        break;
                }

                case 4:

                    ConsultContact();
                    break;
                case 5:
                    
                    if(contacts.size() != 0){

                        System.out.println("Showing all contacts of the contact manager");
                        
                        for(int i = 0; i < contacts.size(); i++){

                            System.out.println(contacts.get(i));
                        }
                    }

                    else{

                        System.out.println("There are no contacts in the database");
                    }

                    break;
                default:
                    System.out.println("Not a valid option. Try again. [0-5]");
            }

        }

    }
*/
    /**
    * Function that check the existence of a contact with a given email
    * 
    * @param contact Contact for searching of
    * @return boolean value, true if it exists; false if it doesn't
    *
    **/

    public boolean checkExistence(Contact contact){

        if(DAOContact.QueryByEmail(contact) == null){

            return false;
        }

        else{

            return true;
        }
    }

/**
 * A visual menu to see Search options
 *
 **/
/*
public void ConsultContact(){

    int option;

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

            if((single = SearchContactByEmail(search_term)) != null){

                System.out.println("Showing the result of the search: ");
                System.out.println(single.toString());
            }

            else{

                System.out.println("No results.");
            }
        }

        else if(option == 2){

            System.out.println("Type the full name of the contact to search: ");

            in = new Scanner (System.in);

            search_term = in.nextLine();

            if((compound = SearchContactByFullname(search_term)) != null){

                System.out.println("Showing the results of the search: ");

                for(int i = 0; i < compound.size(); i++){

                    System.out.println(compound.get(i).toString());
                }
            }

            else{

                System.out.println("No results.");
            }
        }

        else if(option == 3){

            System.out.println("Type the interest of the contact to search: ");
            search_term = in.next();

            if((compound = SearchContactByInterest(search_term.toUpperCase())) != null){

                System.out.println("Showing the results of the search: ");

                for(int i = 0; i < compound.size(); i++){

                    System.out.println(compound.get(i).toString());
                }
            }

            else{

                System.out.println("No results.");
            }
        }

        else if (option == 4){

            System.out.println("Type the age of the contact to search: ");
            search_term_int = in.nextInt();


            if((compound = SearchContactByAge(search_term_int)) != null){

                System.out.println("Showing the results of the search: ");

                for(int i = 0; i < compound.size(); i++){

                    System.out.println(compound.get(i).toString());
                }
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


*/
}
