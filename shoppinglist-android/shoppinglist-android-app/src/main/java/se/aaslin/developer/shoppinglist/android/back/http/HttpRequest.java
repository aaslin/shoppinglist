package se.aaslin.developer.shoppinglist.android.back.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest.Alias;

import android.net.Proxy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;

public class HttpRequest<T> {
	public static class Alias {
		private final String alias;
		private final Class<?> clazz;
		
		public Alias(String alias, Class<?> clazz) {
			this.alias = alias;
			this.clazz = clazz;
		}

		public String getAlias() {
			return alias;
		}

		public Class<?> getClazz() {
			return clazz;
		}
	}
	
	private Class<T> clazz;
	private Class<? extends Collection> collectionClazz;
	private Map<String, String> cookies = new HashMap<String, String>();	
	
	public HttpRequest(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public HttpRequest(Class<T> clazz, Class<? extends Collection> collectionClazz) {
		this.clazz = clazz;
		this.collectionClazz = collectionClazz;
	}

	public HttpRequest<T> setCookie(String key, String value) {
		cookies.put(key, value);
		return this;
	}
	
	public HttpResponse<T> doGet(URI uri, Alias... aliases) {
		try {
			org.apache.http.HttpResponse httpResponse = get(uri);
			
			if (clazz.equals(Void.class)) {
				return new HttpResponse<T>(new ArrayList<T>(), httpResponse.getStatusLine().getStatusCode());
			}
			
			if (collectionClazz == null) {
				@SuppressWarnings("unchecked")
				T responseEntity = (T) ingestResponse(httpResponse, aliases);
				return new HttpResponse<T>(responseEntity, httpResponse.getStatusLine().getStatusCode());
			} else {
				@SuppressWarnings("unchecked")
				List<T> responseEntities = (List<T>) ingestResponse(httpResponse, aliases);
				return new HttpResponse<T>(responseEntities, httpResponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public HttpResponse<T> doPost(URI uri, Object request, Alias... aliases) {
		try {
			String requestEntity = marshall(request);
			org.apache.http.HttpResponse httpResponse = post(uri, requestEntity);
			
			if (clazz.equals(Void.class)) {
				return new HttpResponse<T>(new ArrayList<T>(), httpResponse.getStatusLine().getStatusCode());
			}
			
			if (collectionClazz == null) {
				@SuppressWarnings("unchecked")
				T responseEntity = (T) ingestResponse(httpResponse, aliases);
				return new HttpResponse<T>(responseEntity, httpResponse.getStatusLine().getStatusCode());
			} else {
				@SuppressWarnings("unchecked")
				List<T> responseEntities = (List<T>) ingestResponse(httpResponse, aliases);
				return new HttpResponse<T>(responseEntities, httpResponse.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public HttpResponse<T> doDelete(URI uri) {
		try {
			org.apache.http.HttpResponse httpResponse = delete(uri);
			return new HttpResponse<T>(new ArrayList<T>(), httpResponse.getStatusLine().getStatusCode());
	
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Object ingestResponse(org.apache.http.HttpResponse httpResponse, Alias... aliases) throws IOException {
		Object response;
		if (clazz.isAnnotationPresent(XStreamAlias.class)) {
			XStream stream = getDefaultXStream();
			for (Alias a : aliases) {
				stream.alias(a.getAlias(), a.getClazz());
			}
//			stream.processAnnotations(clazz);
			XStreamAlias clazzAlias = clazz.getAnnotation(XStreamAlias.class);
			String alias = clazzAlias.value();
			stream.alias(alias, clazz);
			if (collectionClazz != null) {
				stream.alias(alias + "es", collectionClazz);
			}
			response = stream.fromXML(httpResponse.getEntity().getContent());
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			if (sb.length() > 0) {
				sb.replace(sb.length() -1, sb.length(), "");
			}
			if (clazz.equals(String.class)) {
				response = sb.toString();
			} else if (clazz.equals(Integer.class)) {
				response = Integer.parseInt(sb.toString());
			} else if (clazz.equals(Double.class)) {
				response = Double.parseDouble(sb.toString());
			} else {
				throw new RuntimeException(String.format("%s is not yet supported", clazz.getCanonicalName()));
			}
		}		
		
		return response;
	}

	private XStream getDefaultXStream() {
		XStream stream = new XStream();
		stream.registerConverter(new ISO8601DateConverter());
		return stream;
	}

	private org.apache.http.HttpResponse post(URI uri, String request) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpRequest = new HttpPost();
		httpRequest.setURI(uri);
		httpRequest.setHeader("Content-Type", "application/xml");
		httpRequest.setHeader("Accept", "application/xml");
		addCookies(httpRequest);
		
		HttpEntity httpEntity = new StringEntity(request);
		httpRequest.setEntity(httpEntity);
		
		return client.execute(httpRequest);
	}
	
	private org.apache.http.HttpResponse get(URI uri) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet();
		httpRequest.setURI(uri);
		httpRequest.setHeader("Accept", "application/xml");
		addCookies(httpRequest);
		
		return client.execute(httpRequest);
	}
	
	private org.apache.http.HttpResponse delete(URI uri) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpDelete httpRequest = new HttpDelete();
		httpRequest.setURI(uri);
		httpRequest.setHeader("Content-Type", "application/xml");
		httpRequest.setHeader("Accept", "application/xml");
		addCookies(httpRequest);
				
		return client.execute(httpRequest);
	}
	
	private void addCookies(HttpRequestBase httpRequest) {
		StringBuilder sb =  new StringBuilder();
		if (cookies == null) {
			return;
		}
		for (Map.Entry<String, String> cookie : cookies.entrySet()) {
			sb.append(cookie.getKey()).append("=").append(cookie.getValue()).append(";"); 		
		}
		if (sb.length() > 0) {
			Header header = new BasicHeader("Cookie", sb.substring(0, sb.length() - 1).toString());
			httpRequest.addHeader(header);
		}
	}
	
	private String marshall(Object request) {
		XStream stream = getDefaultXStream();
		if (request.getClass().isAnnotationPresent(XStreamAlias.class)) {
//			XStreamAlias clazzAlias = request.getClass().getAnnotation(XStreamAlias.class);
//			String alias = clazzAlias.value();
//			stream.alias(alias, request.getClass());
			ClassAliasingMapper mapper = new ClassAliasingMapper(stream.getMapper());
			mapper.addClassAlias("member", String.class);
			stream.registerLocalConverter(
			    ShoppingListDTO.class,
			    "members",
			    new CollectionConverter(mapper)
			);
			stream.processAnnotations(request.getClass());
		}
		
		return stream.toXML(request);
	}
}
