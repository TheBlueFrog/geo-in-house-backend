package com.ebay.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ebay.mike.geodb.InstallationRecord;

/**
 * add a new installation
 */
public class PutNewInstallation
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = PutInstallation we get here
	 * we return a new installation record

	 * args
	 * [0] = new installation GUID
	 * [1] = display name
	 * [2] = new GCM registration ID 
	 */
	
	public static void main(String[] args)
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    Connection db = null;

		    try
		    {
		      db = DriverManager.getConnection("jdbc:sqlite:fencenotification.db");

		      InstallationRecord r = new InstallationRecord(db, args[0], args[1], args[2]);
		      
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
