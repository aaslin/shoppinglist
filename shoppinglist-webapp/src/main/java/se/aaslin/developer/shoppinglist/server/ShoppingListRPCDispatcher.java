package se.aaslin.developer.shoppinglist.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import se.aaslin.developer.shoppinglist.annotation.Url;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

public class ShoppingListRPCDispatcher implements Filter, SerializationPolicyProvider {

	private static final String GWT_PATH = "/gwt.";
	private final Map<String, SerializationPolicy> serializationPolicyCache = new HashMap<String, SerializationPolicy>();

	@Autowired ApplicationContext context;

	Map<String, Class<?>> managedBeans = new HashMap<String, Class<?>>();
	ServletContext servletContext;
	String contextPath;	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		contextPath = filterConfig.getServletContext().getContextPath();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory.autowireBean(this);

		List<Class<?>> beans = findBeans();
		for (Class<?> bean : beans) {
			Url url = bean.getAnnotation(Url.class);
			Class<?>[] interfaces = bean.getInterfaces();
			managedBeans.put(url.value(), interfaces[0]);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getRequestURI().replace(request.getContextPath(), "");
		if (path.startsWith(GWT_PATH) && !path.endsWith(".js") && !path.endsWith(".html")) {
			try {
				String url = path.substring(path.lastIndexOf("/") + 1, path.length());
				Class<?> clazz = managedBeans.get(url);
				Object bean = context.getBean(clazz);
				String payload = RPCServletUtils.readContentAsGwtRpc(request);

				String responsePayload = processCall(payload, bean);
				writeResponse(request, response, responsePayload);
			} catch (Throwable caught) {
				RPCServletUtils.writeResponseForUnexpectedFailure(servletContext, response, caught);
			}
		} else {
			filterChain.doFilter(req, res);
		}
	}

	private String processCall(String payload, Object bean) throws SerializationException {
		try {
			RPCRequest rpcRequest = RPC.decodeRequest(payload, bean.getClass(), this);
			return RPC.invokeAndEncodeResponse(bean, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());
		} catch (IncompatibleRemoteServiceException ex) {
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	private void writeResponse(HttpServletRequest request, HttpServletResponse response, String responsePayload) throws IOException {
		boolean gzipEncode = RPCServletUtils.acceptsGzipEncoding(request) && shouldCompressResponse(request, response, responsePayload);
		RPCServletUtils.writeResponse(servletContext, response, responsePayload, gzipEncode);
	}

	private boolean shouldCompressResponse(HttpServletRequest request, HttpServletResponse response, String responsePayload) {
		return RPCServletUtils.exceedsUncompressedContentLengthLimit(responsePayload);
	}

	private static List<Class<?>> findBeans() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

		scanner.addIncludeFilter(new AnnotationTypeFilter(Url.class));

		// scanner.addIncludeFilter(new
		// AssignableTypeFilter(RemoteService.class));
		List<Class<?>> result = new ArrayList<Class<?>>();

		for (BeanDefinition def : scanner.findCandidateComponents("se.aaslin.developer.shoppinglist.server")) {
			try {
				Class<?> clazz = (Class<?>) Class.forName(def.getBeanClassName());
				result.add(clazz);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}

	@Override
	public SerializationPolicy getSerializationPolicy(String moduleBaseURL, String strongName) {
		SerializationPolicy serializationPolicy = getCachedSerializationPolicy(moduleBaseURL, strongName);
		if (serializationPolicy != null) {
			return serializationPolicy;
		}

		serializationPolicy = loadSerializationPolicy(moduleBaseURL, strongName);

		if (serializationPolicy == null) {
			// Failed to get the requested serialization policy; use the default
			System.out.println("WARNING: Failed to get the SerializationPolicy '" + strongName + "' for module '" + moduleBaseURL
					+ "'; a legacy, 1.3.3 compatible, serialization policy will be used.  You may experience SerializationExceptions as a result.");
			serializationPolicy = RPC.getDefaultSerializationPolicy();
		}

		// This could cache null or an actual instance. Either way we will not
		// attempt to lookup the policy again.
		putCachedSerializationPolicy(moduleBaseURL, strongName, serializationPolicy);

		return serializationPolicy;
	}

	private SerializationPolicy getCachedSerializationPolicy(String moduleBaseURL, String strongName) {
		synchronized (serializationPolicyCache) {
			return serializationPolicyCache.get(moduleBaseURL + strongName);
		}
	}

	private void putCachedSerializationPolicy(String moduleBaseURL, String strongName, SerializationPolicy serializationPolicy) {
		synchronized (serializationPolicyCache) {
			serializationPolicyCache.put(moduleBaseURL + strongName, serializationPolicy);
		}
	}

	private SerializationPolicy loadSerializationPolicy(String moduleBaseURL, String strongName) {
		String modulePath = null;
		if (moduleBaseURL != null) {
			try {
				modulePath = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
		}

		SerializationPolicy serializationPolicy = null;

		/*
		 * Check that the module path must be in the same web app as the servlet
		 * itself. If you need to implement a scheme different than this,
		 * override this method.
		 */
		if (modulePath == null || !modulePath.startsWith(contextPath)) {
			String message = "ERROR: The module path requested, " + modulePath + ", is not in the same web application as this servlet, " + contextPath
					+ ".  Your module may not be properly configured or your client and server code maybe out of date.";
			System.out.println(message);
		} else {
			// Strip off the context path from the module base URL. It should be
			// a
			// strict prefix.
			String contextRelativePath = modulePath.substring(contextPath.length());

			String serializationPolicyFilePath = SerializationPolicyLoader.getSerializationPolicyFileName(contextRelativePath + strongName);

			// Open the RPC resource file read its contents.
			InputStream is = servletContext.getResourceAsStream(serializationPolicyFilePath);
			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader.loadFromStream(is, null);
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					String message = "ERROR: The serialization policy file '" + serializationPolicyFilePath
							+ "' was not found; did you forget to include it in this deployment?";
					System.out.println(message);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}
		}

		return serializationPolicy;
	}
}
