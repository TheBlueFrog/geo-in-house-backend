package com.ebay.mike.geodb;

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
