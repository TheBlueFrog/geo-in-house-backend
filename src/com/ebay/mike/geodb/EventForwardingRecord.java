package com.ebay.mike.geodb;

/**
	CREATE TABLE EventForwardings
	    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	    FenceID INTEGER,
	    InstallationID INTEGER,
	    IncomingEventType INTEGER);
 
 */
public class EventForwardingRecord extends AbstractRecord
{
	public long mID;
	public long mFenceID;
	public long mInstallationID;
	public int mIncomingEventType;
	
	
	
	public EventForwardingRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split(FIELD_SEPARATOR);
		
		mID = Long.parseLong(a[0]);
		mFenceID = Long.parseLong(a[1]);
		mInstallationID = Long.parseLong(a[2]);
		mIncomingEventType = Integer.parseInt(a[3]);
	}
	
	public String toString() 
	{
		return String.format("%s%d%s%d%s%d%s%d%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mFenceID,
				FIELD_SEPARATOR,
				mInstallationID, 
				FIELD_SEPARATOR,
				mIncomingEventType,
				END_SYMBOL);
	}

}
