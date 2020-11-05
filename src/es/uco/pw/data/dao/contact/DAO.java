package es.uco.pw.data.dao.contact;

import java.text.ParseException;
import java.util.ArrayList;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.business.contact.ContactManager;

public class DAO {
	
	public static void main(String[] args) throws ParseException {
		
		ArrayList <Contact> contacts = new ArrayList<Contact>();

		Contact aux = new Contact();
		aux.setName("Pedro Pablo");
		aux.setSurname("García Pozo");
		aux.setBirthday(null);
		aux.setEmail("pedropgarciap@gmail.com");

		contacts = DAOContact.QueryByFullname(aux);

		for (Contact contact : contacts) {
			
			System.out.println(contact.toString());
		}


		/*ContactManager system = ContactManager.getInstance();

		system.Menu();*/
	}
}
