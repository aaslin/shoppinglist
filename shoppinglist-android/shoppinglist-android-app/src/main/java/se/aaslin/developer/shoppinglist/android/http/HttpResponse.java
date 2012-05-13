package se.aaslin.developer.shoppinglist.android.http;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse<T> {
	
	private List<T> entities = new ArrayList<T>();
	private int statusCode;
	
	public HttpResponse(List<T> entities, int status) {
		this.entities.addAll(entities);
		this.statusCode = status;
	}

	public HttpResponse(T entity, int statusCode) {
		entities.add(entity);
		this.statusCode = statusCode;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}
	
	public T getSingleEntity() {
		return entities.get(0);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
