package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginDTO implements Serializable {

	private static final long serialVersionUID = 5940090247116791753L;
	
	private String username;
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
