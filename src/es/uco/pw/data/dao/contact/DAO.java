package es.uco.pw.data.dao.contact;

import java.text.ParseException;
import es.uco.pw.business.contact.ContactManager;



/**
 * A class to represent the main of the program
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */




public class DAO {
	
	public static void main(String[] args) throws ParseException {
		
		ContactManager system = ContactManager.getInstance();

		system.Menu();
	}
}
