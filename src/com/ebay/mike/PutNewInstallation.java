package com.ebay.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ebay.mike.geodb.InstallationRecord;

/**
 * add a new fence
 */
public class PutNewInstallation
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = PutNewInstallation we get here
	 * we return the toString of the new installation record

	 * args are a map
	 * 
	 *  params.put("InstallationGUID", installGuid);
		params.put("DisplayName", displayName);
		params.put("GCMRegistrationID", gcmRegistrationID);
	 */
	
	public static void main(String[] args)
	{
		Map<String, String> params = new HashMap<String, String>();
		
		for (int i = 0; i < args.length; i += 2)
		{
			params.put(args[i], args[i+1]);
			System.out.println(String.format("%s = %s", args[i], args[i+1]));
		}

	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    Connection db = null;

		    try
		    {
		      db = DriverManager.getConnection("jdbc:sqlite:/data/fencenotification.db");

		      InstallationRecord r = new InstallationRecord(db, 
		    		  params.get("InstallationGUID"), 
		    		  params.get("DisplayName"), 
		    		  params.get("GCMRegistrationID"));
		      
		      System.out.println(r.toString());
		    }
		    catch(SQLException e)
		    {
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(db != null)
		          db.close();
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
