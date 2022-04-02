public class User {
	
	private String name;
	private String pass;
	private boolean admin;

public User(String startName, String startPass, boolean startAdmin) {
	
	name = startName;
	name = startName;
	admin = startAdmin;
	
}

public void setUsername(String newName) {
	name = newName;
}

public void setPass(String newPass) {
	pass = newPass;
}

public void setAdmin (boolean newAdmin) {
	admin = newAdmin;
}

public String getName () {
	return name;
}

public String getPass () {
	return pass;
}

public boolean getAdmin () {
	return admin;
}

}
