package es.uco.pw.data.dao.post;

import java.text.ParseException;
import es.uco.pw.business.post.PostManager;

public class DAO {

    public static void main(String[] args) throws ParseException {
		
		PostManager system = PostManager.getInstance();

		system.Menu();
	}
}
