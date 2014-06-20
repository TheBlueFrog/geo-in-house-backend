package com.ebay.mike;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 */
public class SendMessage
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * with params in args[] as name-value pairs
	 * 
	 * args
	 * [0, 1] = "OpCode", "SendMessage"
	 * [2, 3] = name, value
	 */
	
	static String mIdentityProvider		= "EbayInc";
	static String mSOAAppName 			= "eBayInc73-c2b2-4710-876a-f3184aabbe8";
	static String mApplication			= "FRLIB_TEST_2";
	static String mDomain 				= "frlibtest";
	static String mUserName         	= "mike@fred";
	static String mEventName			= "ping";
	static String mMessage   			= "";
	
	
	static String mPayload 				= 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+ "<MessagePayload>"
			+   "<AlertText>Notification</AlertText>"       
			+   "<Level1Data>"
			+   "--body--"
//			+     "<Data>"                                              
//			+       "<Key>evt</Key>"                                    
//			+       "<Value>ping</Value>"                               
//			+     "</Data>"                                             
//			+     "<Data>"                                              
//			+       "<Key>usr</Key>"                                    
//			+       "<Value>mike@fred</Value>"                          
//			+     "</Data>"                                             
//			+     "<Data>"                                              
//			+       "<Key>key1</Key>"                                   
//			+       "<Value>value1</Value>"                             
//			+     "</Data>"                                             
			+   "</Level1Data>"                                         
			+ "</MessagePayload>"
			;
	
	public static void main(String[] args)
	{
		// recover params to a map
		
		Map<String, String> params = new HashMap<String, String>();
		
		for (int i = 0; i < args.length; i += 2)
		{
			params.put(args[i], args[i+1]);
			System.out.println(String.format("%s = %s", args[i], args[i+1]));
		}

		// extract params
		
		mSOAAppName = params.get("SOAAppName");
		mDomain = params.get("Domain");
		mApplication = params.get("Application");
		mIdentityProvider = params.get("IdentityProvider");
		mUserName  = params.get("UserName");
		mEventName = params.get("EventName");
		mMessage = params.get("Message");
		
		// and forward to MDNS
		
		try
		{
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try
	        {
	            HttpPost r = new HttpPost("https://mobinotify.ebay.com/mobile/mds/v1/sendMessages");
	            addHeaders (r);
	            addParams (r);
	            addBody (r);

	            
//	            System.out.println("Executing request " + r.getRequestLine());

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
            					throw new ClientProtocolException("Unexpected response status: " + status);
            				}
            			}
            		};
	            		
	            String responseBody = httpclient.execute(r, responseHandler);
//	            Log("----------------------------------------");
	            Log(responseBody);
	        }
	        finally 
	        {
	            httpclient.close();
	        }
	        
			System.out.println("hello");
		}
		catch (UnsupportedEncodingException e)
		{
			Log("UnsupportedEncodingException " + e.getMessage());
		}
		catch (IOException e)
		{
			Log("IOException " + e.getMessage());
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
	
	/* from the docs
	 
<SendMessageRequest>
    <Message>
        <Receiver>
            <!-- There can be multiple id elements -->
            <Id>testuser</Id>
            <Type>User</Type>
            <Provider>SAMPLEPROVIDER</Provider>
            <Domain>SAMPLEDOMAIN</Domain>
            <Application>..SAMPELAPP</Application>
            <!-- or EVENTS-->
            <EventName>SAMPLEEVENT</EventName>
        </Receiver>
        <Data>
            <Payload>
                <![CDATA[{"aps":{"alert":"Test Message!","sound":"default.caf"}}]]>
            </Payload>
            <FormatRequired>false</FormatRequired>
        </Data>
    </Message>
</SendMessageRequest>
	 
	 */
	
	private static void addBody(HttpPost r) throws UnsupportedEncodingException
	{
		String body = mPayload.replace("--body--", mMessage);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<SendMessageRequest>")
		  .append(  "<MsgTier>").append("Tier1").append("</MsgTier>")
		  .append(  "<Message>")
		  .append(    "<Receiver>")
		  .append(      "<Type>User</Type>")
		  .append(      "<Provider>").append(mIdentityProvider).append("</Provider>")
		  .append(      "<Id>").append(mUserName).append("</Id>")
		  .append(      "<Domain>").append(mDomain).append("</Domain>")
		  .append(      "<Application>").append(mApplication).append("</Application>")
		  .append(      "<EventName>").append(mEventName).append("</EventName>")
		  .append(    "</Receiver>")
		  .append(    "<Data>")
          .append(      "<Payload>").append("<![CDATA[").append(body).append("]]>").append("</Payload>")
		  .append(      "<FormatRequired>true</FormatRequired>")
		  .append(    "</Data>")
		  .append(  "</Message>")
		  .append("</SendMessageRequest>")
		  ;
		
		Log(sb.toString());
		
		StringEntity xmlEntity = new StringEntity(sb.toString(), "UTF-8");
		r.setEntity(xmlEntity );
	}

	private static void addHeaders(HttpPost r)
	{
		Header[] v = new Header[] 
		{
			new BasicHeader("Content-Type", "application/xml"),
			new BasicHeader("Authorization", "APP " + mSOAAppName),
			new BasicHeader("Accept", "application/xml"),
		};
		Log(v);
		
        r.setHeaders(v);
	}

	private static void addParams(HttpPost r) throws UnsupportedEncodingException
	{
//	    ArrayList<BasicNameValuePair> v = new ArrayList<BasicNameValuePair>();
//	    v.add(new BasicNameValuePair("GUID", mInstallationGUID));
//	    v.add(new BasicNameValuePair("GCMID", mGCMRegistrationID));
//	    v.add(new BasicNameValuePair("AppID", mAppID));
//
//	    r.setEntity(new UrlEncodedFormEntity(v, "UTF-8"));
	}

	/*
	 	https://mobinotify.ebay.com/mobile/mds/v1/sendMessages
	 	Content-Type: application/xml
		Authorization: eBayInc73-c2b2-4710-876a-f3184aabbe8
		Accept: application/xml

<SendMessageRequest>
  <MsgTier>Tier1</MsgTier>
  <Message>
    <Receiver>
      <Type>User</Type>
      <Provider>EbayInc</Provider>
      <Id>mike@fred</Id>
      <Domain>frlibtest</Domain>
      <Application>FRLIB_TEST_2</Application>
      <EventName>ping</EventName>
    </Receiver>
    <Data>
      <Payload>
        <![CDATA[<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
          <MessagePayload>
            <AlertText>Test Notification PING!!</AlertText>
            <Level1Data>
              <Data>
                <Key>evt</Key>
                <Value>ping</Value>
              </Data>
              <Data>
                <Key>usr</Key>
                <Value>mike@fred</Value>
              </Data>
              <Data>
                <Key>key1</Key>
                <Value>value1</Value>
              </Data>
            </Level1Data>
          </MessagePayload>]]>
      </Payload>
      <FormatRequired>true</FormatRequired>
    </Data>
  </Message>
</SendMessageRequest>

	 
	 */
}
