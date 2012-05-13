package se.aaslin.developer.shoppinglist.android.back.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("loginDTO")
public class LoginDTO {
	
	@XStreamAlias("username")
	private String username;
	
	@XStreamAlias("password")
	private String password;
	
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
