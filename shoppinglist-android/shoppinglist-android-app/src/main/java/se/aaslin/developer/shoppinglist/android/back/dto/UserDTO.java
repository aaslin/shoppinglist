package se.aaslin.developer.shoppinglist.android.back.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("userDTO")
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 459406204363352921L;

	@XStreamAlias("username")
	private String username;
	
	@XStreamAlias("status")
	private String status;

	public UserDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}