package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

public class AuthenticationDTO implements Serializable {
	
	private static final long serialVersionUID = 5117001723272028999L;
	private String username;
	private String[] roles;
	
	public AuthenticationDTO(String username, String[] roles) {
		this.username = username;
		this.roles = roles;
	}

	public AuthenticationDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}
