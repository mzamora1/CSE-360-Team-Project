package restaurant;

import restaurant.users.Customer;
import restaurant.users.GuestUser;

public class User {

	private String name;
	private String pass;
	private boolean admin;

	public User(String startName, String startPass, boolean startAdmin) {
		name = startName;
		pass = startPass;
		admin = startAdmin;
	}

	public void setUsername(String newName) {
		name = newName;
	}

	public void setPass(String newPass) {
		pass = newPass;
	}

	public void setAdmin(boolean newAdmin) {
		admin = newAdmin;
	}

	public String getName() {
		return name;
	}

	public String getPass() {
		return pass;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isGuest() {
		return this instanceof GuestUser;
	}

	public boolean isCustomer() {
		return this instanceof Customer;
	}
}
