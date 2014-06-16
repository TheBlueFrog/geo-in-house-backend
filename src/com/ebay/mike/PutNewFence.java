package com.ebay.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ebay.mike.geodb.FenceRecord;

/**
 * add a new fence to an installation
 */
public class PutNewFence
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = PutNewFence we get here
	 * we return a new FenceRecord

	 * args
	 * [0] owning installation GUID
	 * [1] display name
	 * [2] latitude 
	 * [3] longitude
	 * [4] radius
	 * [5] events
	 * [6] URI
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

		      FenceRecord r = new FenceRecord(db, 
		    		  args[0], 						// installation GUID
		    		  args[1], 						// displayName
		    		  Double.parseDouble(args[2]),	// lat
		    		  Double.parseDouble(args[3]),	// lon
		    		  Double.parseDouble(args[4]),	// radius
		    		  Integer.parseInt(args[5]),	// events
		    		  args[6]);						// URI

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
