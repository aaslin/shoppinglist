package se.aaslin.developer.shoppinglist.test.system.ws;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class BaseWsTest {

	protected static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/shoppinglist/").build();
	}
}
