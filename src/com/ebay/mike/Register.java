package com.ebay.mike;

import java.util.HashMap;
import java.util.Map;


public class Register
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * with params 
	 * 
	 * @param args
	 * should be pairs (name value) because the client used a Map
	 * 
		p.put("GUID", mGUID);
		p.put("RegistrationID", mRegistrationID);
		p.put("AppID", mAppID);
		
		and that got flattened out
	 */
	public static void main(String[] args)
	{
		// recover to a map
		
		Map<String, String> params = new HashMap<String, String>();
		
		for (int i = 0; i < args.length; i += 2)
		{
			params.put(args[i], args[i+1]);
			System.out.println(String.format("%s = %s", args[i], args[i+1]));
		}

		// extract params
		
		String GUID = params.get("GUID");
		String GCM = params.get("RegistrationID");
		String AppID = params.get("AppID");
		
//		try
//		{
//	       CloseableHttpClient httpclient = HttpClients.createDefault();
//	        try
//	        {
//	            HttpPost r = new HttpPost("mdns...");
//	            addHeaders (r);
//	            addParams (r, GUID, GCM, AppID);
////	            addBody (GUID, GCM, AppID);
//	            
////	            System.out.println("Executing request " + r.getRequestLine());
//
//	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() 
//	            		{
//	            			public String handleResponse(
//	            				final HttpResponse response) throws ClientProtocolException, IOException 
//	            			{
//	            				int status = response.getStatusLine().getStatusCode();
//	            				if (status >= 200 && status < 300) 
//	            				{
//	            					HttpEntity entity = response.getEntity();
//	            					return entity != null ? EntityUtils.toString(entity) : null;
//	            				}
//	            				else
//	            				{
//	            					throw new ClientProtocolException("Unexpected response status: " + status);
//	            				}
//	            			}
//	            		};
//	            		
//	            String responseBody = httpclient.execute(r, responseHandler);
//	            System.out.println("----------------------------------------");
//	            System.out.println(responseBody);
//	        }
//	        finally 
//	        {
//	            httpclient.close();
//	        }
//	        
//			System.out.println("hello");
//		}
//		catch (IOException e)
//		{
//			System.out.println("IOException " + e.getMessage());
//		}
		
	}

//	private static void addHeaders(HttpPost r)
//	{
//		Header[] v = new Header[0];
//        r.setHeaders(v);
//	}
//
//	private static void addParams(HttpPost r, String guid, String gcm, String appID) throws UnsupportedEncodingException
//	{
//	    ArrayList<BasicNameValuePair> v = new ArrayList<BasicNameValuePair>();
//	    v.add(new BasicNameValuePair("GUID", guid));
//	    v.add(new BasicNameValuePair("GCMID", gcm));
//	    v.add(new BasicNameValuePair("AppID", appID));
//
//	    r.setEntity(new UrlEncodedFormEntity(v, "UTF-8"));
//	}

}
