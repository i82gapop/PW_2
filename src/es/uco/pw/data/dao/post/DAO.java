package es.uco.pw.data.dao.post;

import java.text.ParseException;
import es.uco.pw.business.post.PostManager;


/**
 * A class to represent the main of the board manager program
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */


public class DAO {

    public static void main(String[] args) throws ParseException {
		
		PostManager system = PostManager.getInstance();

		system.Menu();
	}
}
