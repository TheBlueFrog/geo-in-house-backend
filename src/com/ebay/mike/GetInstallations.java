package com.ebay.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.ebay.mike.geodb.DB;
import com.ebay.mike.geodb.InstallationRecord;

/**
 * retrieve known installations
 */
public class GetInstallations
{
	/**
	 * app does a GET to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = GetInstallations we get here
	 * we output all known Installation records, stringified
	 * 
	 * no args
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

		      List<Long> v = DB.getAllInstallationIDs(db);
		      for (Long i : v)
		      {
		    	  InstallationRecord r = new InstallationRecord(db, i);
		    	  System.out.println(r.toString());
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
