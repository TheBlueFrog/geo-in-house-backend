package com.ebay.mike.geodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
	class to wrap SQLite3 table row
	
	CREATE TABLE Installations 
		(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
		 Guid TEXT, 
		 DisplayName TEXT, 
		 GCMRegistrationID TEXT);
		 
 */
public class InstallationRecord extends AbstractRecord 
{
	/** record id */
	public long mID;

	/** GUID of the device installation represent */
	public String mGuid;
	
	public String mDisplayName;
	
	/** Google Cloud Messaging registration ID */
	public String mGCMRegistrationID;

	/**
	 * construct and insert into DB
	 * 
	 * @param db
	 * @param guid
	 * @param displayName
	 * @param gcmRegistrationID
	 */
	public InstallationRecord(Connection db, String guid, String displayName, String gcmRegistrationID)
	{
		mID = -1;
		mGuid = guid;
		mDisplayName = displayName;
		mGCMRegistrationID = gcmRegistrationID;
		
		PreparedStatement s = null;
		try
		{
			List<String> v = new ArrayList<String>();

			String q = String.format("insert into Installations (Guid, DisplayName, GCMRegistrationID) values (\"%s\", \"%s\", \"%s\")",
					mGuid,
					mDisplayName,
					mGCMRegistrationID);
			
			s = db.prepareStatement(q);
			s.executeUpdate();
			s.close();

			q = String.format("select _id from Installations order by _id DESC limit 1");                				
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				mID = rs.getLong(1);
			else
				throw new IllegalStateException("Failed to insert and recover _id");
		}
		catch (SQLException e)
		{
			e.printStackTrace(System.out);
		}
		finally
		{
			if (s != null)
				try 
				{
					s.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace(System.out);
				}
		}

	}
	
	/** extract from DB at given record 
	 * 
	 * @param db
	 * @param id
	 */
	public InstallationRecord(Connection db, Long id) 
	{
		PreparedStatement s = null;
		try
		{
			String q = String.format("select * from Installations where (_id = %d) order by _id DESC limit 1", id);                				
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
			{
				mID = rs.getLong(1);
				mGuid = rs.getString(2);
				mDisplayName = rs.getString(3);
				mGCMRegistrationID = rs.getString(4);
				
				// fish out the other pieces
				// 
			}
			else
				throw new IllegalStateException("Failed to get record with row ID " + id);
		}
		catch (SQLException e)
		{
			e.printStackTrace(System.out);
		}
		finally
		{
			if (s != null)
				try 
				{
					s.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace(System.out);
				}
		}
	}

	/** construct from string?
	 * 
	 * @param s
	 */
	public InstallationRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split(FIELD_SEPARATOR);
		
		mID = Long.parseLong(a[0]);
		mGuid = a[1];
		mDisplayName = a[2];
		mGCMRegistrationID = a[3];
	}
	
	public String toString() 
	{
		return String.format("%s%d%s%s%s%s%s%s%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mGuid,
				FIELD_SEPARATOR,
				mDisplayName, 
				FIELD_SEPARATOR,
				mGCMRegistrationID,
				END_SYMBOL);
	}
}
