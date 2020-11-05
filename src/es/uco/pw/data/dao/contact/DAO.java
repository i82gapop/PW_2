package es.uco.pw.data.dao.contact;

import java.text.ParseException;

import es.uco.pw.business.contact.ContactManager;

public class DAO {
	
	public static void main(String[] args) throws ParseException {
		
		/*ArrayList <Contact> contacts = new ArrayList<Contact>();

		contacts = DAOContact.QueryByName("Pedro");

		for (Contact contact : contacts) {
			
			System.out.println(contact.toString());
		}*/


		ContactManager system = ContactManager.getInstance();

		system.Menu();
	}
}
