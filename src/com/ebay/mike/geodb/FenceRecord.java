package com.ebay.mike.geodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
class to wrap SQLite3 table row

CREATE TABLE Fences
    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
    InstallationID INTEGER, 
    Guid TEXT, 
    DisplayName TEXT,
 	Latitude NUMBER, Longitude NUMBER, 
 	Radius NUMBER, 
 	Events INTEGER, 
 	URI TEXT);
	 
*/
public class FenceRecord extends AbstractRecord
{
	public long mID;
	public long mInstallationID;
	public String mGuid;
	public String mDisplayName;
	public double mLatitude;
	public double mLongitude;
	public double mRadius;
	public int mEvents;
	public String mURI;
	
	
	public FenceRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split(FIELD_SEPARATOR);
		
		mID = Long.parseLong(a[0]);
		mInstallationID = Long.parseLong(a[1]);
		mGuid = a[2];
		mDisplayName = a[3];
		mLatitude = Double.parseDouble(a[4]);
		mLongitude = Double.parseDouble(a[5]);
		mRadius = Double.parseDouble(a[6]);
		mEvents = Integer.parseInt(a[7]);
		mURI = a[8];
	}
	
	public FenceRecord(Connection db, 
			String installationGuid, 
			String displayName, 
			double latitude, double longitude, double radius,
			int events, 
			String uri) throws SQLException
	{
		PreparedStatement s = null;
		try
		{
			mID = -1;
			mInstallationID = DB.getGuidID(db, installationGuid);
			mGuid = UUID.randomUUID().toString();
			mDisplayName = displayName;
			mLatitude = latitude;
			mLongitude = longitude;
			mRadius = radius;
			mEvents = events;
			mURI = uri;

			List<String> v = new ArrayList<String>();

			String q = String.format("insert into Fences (InstallationID, Guid, DisplayName, Latitude, Longitude, Radius, Events, URI) "
					+ "values (%d, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%d\", \"%s\")",
					mInstallationID,
					mGuid,
					mDisplayName,
					Double.toString(mLatitude),
					Double.toString(mLongitude),
					Double.toString(mRadius),
					mEvents,
					mURI);
			
			s = db.prepareStatement(q);
			s.executeUpdate();
			s.close();

			q = String.format("select _id from Fences order by _id DESC limit 1");                				
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				mID = rs.getLong(1);
			else
				throw new IllegalStateException("Failed to insert and recover _id");
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
	
	
	public FenceRecord(ResultSet rs) throws SQLException 
	{
		mID = rs.getLong(1);
		mInstallationID = rs.getLong(2);
		mGuid = rs.getString(3);
		mDisplayName = rs.getString(4);
		mLatitude = rs.getDouble(5);
		mLongitude = rs.getDouble(6);
		mRadius = rs.getDouble(7);
		mEvents = rs.getInt(8);
		mURI = rs.getString(9);
	}

	public String toString() 
	{
		return String.format("%s%d%s%d%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mInstallationID,
				FIELD_SEPARATOR,
				mGuid, 
				FIELD_SEPARATOR,
				mDisplayName,
				FIELD_SEPARATOR,
				mLatitude,
				FIELD_SEPARATOR,
				mLongitude,
				FIELD_SEPARATOR,
				mRadius,
				FIELD_SEPARATOR,
				mEvents,
				FIELD_SEPARATOR,
				mURI,
				END_SYMBOL);
	}
	
}
