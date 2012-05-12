package se.aaslin.developer.shoppinglist.android.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoginDTO {

	@Element
	private String username;
	
	@Element
	private String password;
	
	public LoginDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
