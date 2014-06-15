package com.ebay.mike;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
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

import com.ebay.mike.geodb.DB;

/**
 * retrieve known installations
 */
public class GetInstallations
{
	/**
	 * app does a GET to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * with params in args[] as name-value pairs
	 * 
	 * if OpCode = GetInstallations we get here
	 * we output known installation GUIDs
	 */
	
	public static void main(String[] args)
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    Connection connection = null;

		    try
		    {
		      Connection db = DriverManager.getConnection("jdbc:sqlite:sample.db");

		      List<String> v = DB.getAllInstallationGuids(db);
		      for (String s : v)
		      {
		        System.out.println(s);
		      }
		    }
		    catch(SQLException e)
		    {
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        System.out.println(e);
		      }
		    }
		}
	    catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace(System.out);
		}
	}

	static private void Log(String s)
	{
		System.out.println(s);		
	}
	
}
