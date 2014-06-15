package com.ebay.mike.geodb;

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
