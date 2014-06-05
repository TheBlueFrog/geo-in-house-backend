package com.ebay.mike;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Register
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * with params in args[] as name-value pairs
	 * 
`	 */
	
	static String mInstallationGUID;
	static String mGCMRegistrationID;
	static String mAppID;
	static String mAppName;
	static String mSiteID = "EBAY-US";
	static String mDeviceID3PP;
	static String mNotificationSystem;
	static String mMDNSEventDomain;
	static String mLanguage;
	static String mUserName;
	static String mIdentityProvider;
	static Map<String, String> mSubscriptionEvents;
	
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
		
		mInstallationGUID = params.get("InstallationGUID");
		mGCMRegistrationID = params.get("RegistrationID");
		mAppID = params.get("AppID");
		mAppName  = params.get("AppName");
		mSiteID = params.get("SiteID");
		mDeviceID3PP = params.get("3PPDeviceID");
		mNotificationSystem = params.get("NotificationSystem");	// "GCM" = Google, "APNS" = Apple
		mLanguage  = params.get("Language");
		mUserName = params.get("UserName");
		mIdentityProvider = params.get("IdentityProvider");
		mMDNSEventDomain = params.get("MDNSEventDomain");
		
		{
			mSubscriptionEvents = new HashMap<String, String>();
			int i = 0;
			boolean go = true;
			while (go)
			{
				String key = "EventName" + Integer.toString(i);
				if (params.containsKey(key))
				{
					mSubscriptionEvents.put(params.get(key), params.get("EventEnable" + Integer.toString(i)));
					++i;
				}
				else
					go = false;
			}
		}
		
		// and register with MDNS
		
		try
		{
	       CloseableHttpClient httpclient = HttpClients.createDefault();
	        try
	        {
	            HttpPost r = new HttpPost("https://mobinotify.ebay.com/mobile/DeviceNotificationService/v1");
	            addHeaders (r);
	            addParams (r);
	            addBody (r);
	            
//	            System.out.println("Executing request " + r.getRequestLine());

	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() 
	            		{
	            			public String handleResponse(
	            				final HttpResponse response) throws ClientProtocolException, IOException 
	            			{
	            				int status = response.getStatusLine().getStatusCode();
	            				if (status >= 200 && status < 300) 
	            				{
	            					HttpEntity entity = response.getEntity();
	            					return entity != null ? EntityUtils.toString(entity) : null;
	            				}
	            				else
	            				{
	            					throw new ClientProtocolException("Unexpected response status: " + status);
	            				}
	            			}
	            		};
	            		
	            String responseBody = httpclient.execute(r, responseHandler);
	            System.out.println("----------------------------------------");
	            System.out.println(responseBody);
	        }
	        finally 
	        {
	            httpclient.close();
	        }
	        
			System.out.println("hello");
		}
		catch (IOException e)
		{
			System.out.println("IOException " + e.getMessage());
		}
	}

	/* from the docs
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ser="http://www.ebay.com/marketplace/mobile/v1/services">
   <soap:Header/>
   <soap:Body>
        <ser:setMessageSubscription>
            <ser:Domain>SAMPLEDOMAIN..</ser:Domain>
            <ser:MessageAddress>
                <ser:Type>GCM</ser:Type>    --> For iPhone/iPad the Type would be APNS
                <!-- 1 or more repetitions: -->
                <ser:Address>
                    <ser:Name>DT</ser:Name>
                    <!-- This value should be the device token for the current device. -->
                    <ser:Value>acc0e12890612754c5e8acc73ac2916093076e580f44fb9d4bfe13af352110b7</ser:Value>
                </ser:Address>
            </ser:MessageAddress>
            <ser:SubscriberApp>SAMPLEAPPNAME</ser:SubscriberApp>
            <!-- Optional: -->
            <ser:Description>subscription request</ser:Description>
            <!-- Optional: -->
            <ser:Language>en-US</ser:Language>
            <!-- 1 or more repetitions: -->
            <ser:Subscription>
                <ser:Subscriber>
                    <ser:Type>User</ser:Type>
                    <!-- This value is the login name -->
                    <ser:Id>testuser</ser:Id>
                    <ser:IdentityProvider>SAMPLEPROVIDER</ser:IdentityProvider>
                </ser:Subscriber>
                <!-- Optional: -->
                <ser:SecondarySubscriber>
                    <ser:Type>DEVICE_UUID</ser:Type>
                    <!-- This value should be the device UUID for the current device, a 128 bit hex string. A unique device identifier (preferably unique for the life of the device). -->
                    <ser:Id>7e0ae5dfe85a448cb6bcc3c5a59c3abb</ser:Id>
                </ser:SecondarySubscriber>
                <ser:State>Enabled</ser:State>
                <ser:EventGroup>
                    <!-- 1 or more repetitions: -->
                    <ser:Event>
                        <ser:Name>SAMPLEEVENT1</ser:Name>
                        <ser:State>Enabled</ser:State>
                    </ser:Event>
                    <ser:Event>
                        <ser:Name>SAMPLEEVENT2</ser:Name>
                        <ser:State>Enabled</ser:State>
                    </ser:Event>
                </ser:EventGroup>
            </ser:Subscription>
        </ser:setMessageSubscription>
    </soap:Body>
</soap:Envelope>
	 */
	
	private static void addBody(HttpPost r)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ser=\"http://www.ebay.com/marketplace/mobile/v1/services\">")
		  .append(  "<soap:Header/>")
		  .append(  "<soap:Body>")
		  .append(    "<ser:setMessageSubscription>")
		  .append(      "<ser:Domain>").append(mMDNSEventDomain).append("</ser:Domain>")
		  .append(      "<ser:MessageAddress>")
		  .append(        "<ser:Type>").append(mNotificationSystem).append("</ser:Type>")
		  .append(        "<ser:Address>")
		  .append(          "<ser:Name>DT</ser:Name>")
		  .append(          "<ser:Value>").append(mGCMRegistrationID).append("</ser:Value>")
		  .append(        "</ser:Address>")
		  .append(      "</ser:MessageAddress>")
		  .append(      "<ser:SubscriberApp>").append(mAppName).append("</ser:SubscriberApp>")
		  .append(      "<ser:Description>subscription request</ser:Description>")
		  .append(      "<ser:Language>").append(mLanguage).append("</ser:Language>")
		  .append(      "<ser:Subscription>")
		  .append(        "<ser:Subscriber>")
		  .append(            "<ser:Type>User</ser:Type>")
		  .append(            "<ser:Id>").append(mUserName).append("</ser:Id>")
		  .append(            "<ser:IdentityProvider>").append(mIdentityProvider).append("</ser:IdentityProvider>")
		  .append(        "</ser:Subscriber>")
		  .append(        "<ser:SecondarySubscriber>")
		  .append(          "<ser:Type>DEVICE_UUID</ser:Type>")
		  .append(          "<ser:Id>").append(mDeviceID3PP).append("</ser:Id>")
		  .append(        "</ser:SecondarySubscriber>")
		  .append(        "<ser:State>Enabled</ser:State>")
		  .append(        "<ser:EventGroup>");
		  
		for (Entry<String, String> e : mSubscriptionEvents.entrySet())
		sb.append(          "<ser:Event>")
		  .append(            "<ser:Name>").append(e.getKey()).append("</ser:Name>")
		  .append(            "<ser:State>").append(e.getValue()).append("</ser:State>")
		  .append(          "</ser:Event>");
		
		sb.append(        "</ser:EventGroup>")
		  .append(      "</ser:Subscription>")
		  .append(    "</ser:setMessageSubscription>")
		  .append(  "</soap:Body>")
		  .append("</soap:Envelope>");
	}

	private static void addHeaders(HttpPost r)
	{
		Header[] v = new Header[] 
		{
			new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "setMessageSubscription"),
			new BasicHeader("X-EBAY-SOA-SECURITY-APPNAME", mAppID),
			new BasicHeader("X-EBAY-SOA-SERVICE-NAME", "MobileDeviceNotificationService"),
			new BasicHeader("X-EBAY-SOA-MESSAGE-PROTOCOL", "SOAP12"),
			new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "XML"),
			new BasicHeader("X-EBAY-SOA-GLOBAL-ID", mSiteID),
			new BasicHeader("X-EBAY3PP-DEVICE-ID", mDeviceID3PP),
		};
        r.setHeaders(v);
	}

	private static void addParams(HttpPost r) throws UnsupportedEncodingException
	{
	    ArrayList<BasicNameValuePair> v = new ArrayList<BasicNameValuePair>();
	    v.add(new BasicNameValuePair("GUID", mInstallationGUID));
	    v.add(new BasicNameValuePair("GCMID", mGCMRegistrationID));
	    v.add(new BasicNameValuePair("AppID", mAppID));

	    r.setEntity(new UrlEncodedFormEntity(v, "UTF-8"));
	}

}
