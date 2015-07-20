package com.ebay.mike;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class LoginAlamaulaAR
{
		/**
		 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
		 * with params in args[] as name-value pairs
		 * 
		 * args
		 * [0, 1] = "OpCode", "LoginAlamaulaAR"
		 * [2, 3] = name, value
		 */
		
		static String mEndpoint				= "https://api.alamaula.com:8443/api/users/login";
		
		static String mUserName         	= "";
		static String mPassword				= "";
		
		private static String mOtherUserName = "almtestusr";
		private static String mOtherPassword = "Test2014AlmusR";

		public static void main(String[] args)
		{
			// recover params to a map
			
			Map<String, String> params = new HashMap<String, String>();
			
			for (int i = 0; i < args.length; i += 2)
			{
				params.put(args[i], args[i+1]);
				System.out.println(String.format("%s = %s", args[i], args[i+1]));
			}

			// extract optional params

//			if (params.containsKey("Endpoint"))
//				mEndpoint = params.get("Endpoint");
			
			// extract required params
			
			mUserName  = params.get("UserName");
			mPassword  = params.get("Password");
			
			// and forward to the endpoint
			
			String url = String.format("%s?username=%s&password=%s",
					mEndpoint,
					mUserName,
					mPassword);
					
			/*
			 * how to deal with curl --digest parameter
			 * 
	                AndroidHttpClient httpClient = AndroidHttpClient.newInstance("test user agent");

	                URL urlObj = new URL(url);
	                HttpHost host = new HttpHost(urlObj.getHost(), urlObj.getPort(), urlObj.getProtocol());
	                AuthScope scope = new AuthScope(urlObj.getHost(), urlObj.getPort());
	                UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);

	                CredentialsProvider cp = new BasicCredentialsProvider();
	                cp.setCredentials(scope, creds);
	                HttpContext credContext = new BasicHttpContext();
	                credContext.setAttribute(ClientContext.CREDS_PROVIDER, cp);

	                HttpGet job = new HttpGet(url);
	                HttpResponse response = httpClient.execute(host,job,credContext);
	                StatusLine status = response.getStatusLine();
	                Log.d(TestActivity.TEST_TAG, status.toString());
	                httpClient.close();
			 
			 */
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try
			{
				ResponseHandler<String> responseHandler = new ResponseHandler<String>() 
	    		{
	    			public String handleResponse(final HttpResponse response) 
	    					throws ClientProtocolException, IOException 
	    			{
	    				int status = response.getStatusLine().getStatusCode();
	    				if (status >= 200 && status < 300) 
	    				{
	    					HttpEntity entity = response.getEntity();
	    					return entity != null ? EntityUtils.toString(entity) : null;
	    				}
	    				else
	    				{
	    					return "Error - unexpected server status " + status;
//	            					throw new ClientProtocolException("Unexpected response status: " + status);
	    				}
	    			}
	    		};
	    		
	    		URL urlObj = new URL(url);
	            HttpHost host = new HttpHost(urlObj.getHost(), urlObj.getPort(), urlObj.getProtocol());
	            AuthScope scope = new AuthScope(urlObj.getHost(), urlObj.getPort());
	            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(mOtherUserName, mOtherPassword);

	            CredentialsProvider cp = new BasicCredentialsProvider();
	            cp.setCredentials(scope, creds);
	            HttpContext credContext = new BasicHttpContext();
	            credContext.setAttribute( HttpClientContext.CREDS_PROVIDER, cp);

	            HttpPost r = new HttpPost(url);//"https://mobinotify.ebay.com/mobile/mds/v1/sendMessages");
	            addHeaders (r);
//		            addParams (r);
//		            addBody (r);

	            String responseBody = httpclient.execute(host, r, responseHandler, credContext);
	            Log(responseBody);
			}
			catch (IllegalArgumentException e)
			{
				Log("Error - IllegalArgumentException " + e.getMessage());
			}
			catch (UnsupportedEncodingException e)
			{
				Log("Error - UnsupportedEncodingException " + e.getMessage());
			}
			catch (IOException e)
			{
				Log("Error - IOException " + e.getMessage());
			}
	        finally 
	        {
	        	try
	        	{
	        		httpclient.close();
	        	}
	    		catch (IOException e)
	    		{
	    			Log("Error - IOException closing http client" + e.getMessage());
	    		}
	        }
		}

		static private void Log(String s)
		{
			System.out.println(s);		
		}
		static private void Log(Header[] a)
		{
			for(Header h : a)
				Log(h.getName() + ": " + h.getValue());
		}
		

		private static void addHeaders(HttpPost r)
		{
			Header[] v = new Header[] 
			{
				new BasicHeader("Content-Type", "application/json"),
//				new BasicHeader("Authorization", "APP " + mSOAAppName),
//				new BasicHeader("Accept", "application/xml"),
			};
//			Log(v);
			
	        r.setHeaders(v);
		}

		private static void addParams(HttpPost r) throws UnsupportedEncodingException
		{
		}

}
