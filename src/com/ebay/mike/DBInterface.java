package com.ebay.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class DBInterface {

	private static final String TAG = DBInterface.class.getSimpleName();

	protected static String mDBPath = "/data/fencenotification.db";

	
	protected Map<String, String> mParams = new HashMap<String, String>();
    protected Connection mDB = null;

	public DBInterface(String[] args) 
	{
		for (int i = 0; i < args.length; i += 2)
		{
			mParams.put(args[i], args[i+1]);
			Log(String.format("%s = %s", args[i], args[i+1]));
		}
	}

	protected void process()
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    try
		    {
		      mDB = DriverManager.getConnection("jdbc:sqlite:" + mDBPath);

		      String s = innerProcess ();
		      
		      Log(s);
		    }
		    catch(SQLException e)
		    {
		      Log(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(mDB != null)
		          mDB.close();
		      }
		      catch(SQLException e)
		      {
		        Log(e.getMessage());
		      }
		    }
		}
	    catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace(System.out);
		}
	}
	
	protected void Log(String s) {
		System.out.println(s);		
	}

	abstract String innerProcess () throws SQLException;
}
