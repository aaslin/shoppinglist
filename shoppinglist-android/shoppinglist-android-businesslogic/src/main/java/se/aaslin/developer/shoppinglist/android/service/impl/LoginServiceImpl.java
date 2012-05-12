package se.aaslin.developer.shoppinglist.android.service.impl;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ProxyFactory;

import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.android.service.LoginService;
import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.ws.LoginWs;

public class LoginServiceImpl implements LoginService {

	@Override
	public String login(LoginDTO loginDTO) {
		Response response = loginWs.login(loginDTO);
		return (String) response.getEntity();
	}
}
