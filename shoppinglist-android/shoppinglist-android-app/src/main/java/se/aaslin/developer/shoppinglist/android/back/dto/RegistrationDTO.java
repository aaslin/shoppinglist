package se.aaslin.developer.shoppinglist.android.back.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("registrationDTO")
public class RegistrationDTO {

	private String registration;
	
	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}
}
