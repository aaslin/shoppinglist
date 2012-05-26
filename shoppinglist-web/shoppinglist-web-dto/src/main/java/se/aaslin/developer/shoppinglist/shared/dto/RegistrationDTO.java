package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrationDTO implements Serializable {
	
	private static final long serialVersionUID = 7294508579961442653L;
	
	private String registration;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}
}
