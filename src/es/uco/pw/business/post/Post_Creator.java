	package es.uco.pw.business.post;

import es.uco.pw.business.contact.Contact;
import java.util.ArrayList;
import java.sql.Date;

public interface Post_Creator {

	public Post getPost(int id, Type type, String title, String body, Contact owner, Date date_start, Date date_end, ArrayList <String> recipients, ArrayList <String> interests);
	
}
