package se.aaslin.developer.shoppinglist.android.app.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Arrays;

import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import android.content.Context;
import android.os.AsyncTask;

public class RPCUtils {
	public static class RPCProxyHandler implements InvocationHandler {
		
		Context context;
		
		public RPCProxyHandler(Context context) {
			this.context = context;
		}

		@Override
		public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
			Class<?> clazz = method.getDeclaringClass();
			Class<?>[] paramTypes = method.getParameterTypes();
			
			if (!clazz.getCanonicalName().endsWith("Async")) {
				throw new RuntimeException(String.format("Error in %s. RPC service most end with 'Async'"));
			}
			
			int paramCount = paramTypes.length;
			@SuppressWarnings("unchecked")
			final AsyncCallback<Object> callback = (AsyncCallback<Object>) args[paramCount-- - 1];
			
			final Class<?>[] syncParamTypes = new Class[paramCount];
			System.arraycopy(paramTypes, 0, syncParamTypes, 0, paramCount);
			
			String interfaceName = clazz.getCanonicalName().substring(0, clazz.getCanonicalName().length() - 5);
			Class<?> interfaceClass = Class.forName(interfaceName);
			
			final Method syncMethod = interfaceClass.getMethod(method.getName(), syncParamTypes);
			final Object service = InjectionUtils.getInstance(interfaceClass, context);
			
			AsyncTask<Void, Void, Object> asyncTask = new AsyncTask<Void, Void, Object>() {

				@Override
				protected Object doInBackground(Void... arg0) {
					try {
						try {
							return syncMethod.invoke(service, Arrays.copyOfRange(args, 0, args.length - 1));
						} catch (InvocationTargetException e) {
							throw e.getTargetException();
						}
					} catch (Throwable caugth) {
						return caugth;
					}
				}
				
				@Override
				protected void onPostExecute(Object result) {
					if (result instanceof Throwable) {
						Throwable caught = (Throwable) result;
						if (callback != null) {
							callback.onFailure(caught);
						}
					} else {
						if (callback != null) {
							callback.onSuccess(result);
						}
					}
				}
			};
		
			asyncTask.execute();
			
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> clazz, Context context) {
		return (T) Proxy.newProxyInstance(context.getClassLoader(), new Class[] {clazz}, new RPCProxyHandler(context));
	}
	
	private static CookieManager createCookieManager(String authId) {
		try {
			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			HttpCookie cookie = new HttpCookie("auth", authId);
			cookie.setPath(Urls.PATH);
			cookieManager.getCookieStore().add(new URL(Urls.DOMAIN).toURI(), cookie);
		
			return cookieManager;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
