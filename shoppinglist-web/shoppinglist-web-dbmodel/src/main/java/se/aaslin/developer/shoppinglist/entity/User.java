package se.aaslin.developer.shoppinglist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "`user`", uniqueConstraints = @UniqueConstraint(columnNames = {"`username`"}))
public class User implements Serializable{

	private static final long serialVersionUID = 6897471406900567051L;
	
	@Id
	@Column(name = "`ID`", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	
	@Column(name = "`username`", nullable = false)
	private String username;
	
	@Column(name = "`password`", nullable = false)
	private String password;
	
	@Column(name = "`registration`")
	private String registration;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}
}
