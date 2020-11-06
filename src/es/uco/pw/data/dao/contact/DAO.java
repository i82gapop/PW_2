package es.uco.pw.data.dao.contact;

import java.text.ParseException;
import es.uco.pw.business.contact.ContactManager;

public class DAO {
	
	public static void main(String[] args) throws ParseException {
		
		ContactManager system = ContactManager.getInstance();

		system.Menu();
	}
}
