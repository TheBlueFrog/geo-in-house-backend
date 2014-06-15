package com.ebay.mike.geodb;

/**
	CREATE TABLE EvemtForwardingTargets
	    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
		EvenForwardingID INTEGER,
		InstallationID INTEGER
		);
 
 */
public class EventForwardingTargetRecord extends AbstractRecord
{
	public long mID;
	
	/** record ID of EventForwardingRecord we belong to */
	public long mEvenForwardingID;

	/** installation to forward event to */
	public long mInstallationID;
	
	
	public EventForwardingTargetRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split(FIELD_SEPARATOR);
		
		mID = Long.parseLong(a[0]);
		mEvenForwardingID = Long.parseLong(a[1]);
		mInstallationID = Long.parseLong(a[2]);
	}
	
	public String toString() 
	{
		return String.format("%s%d%s%d%s%d%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mEvenForwardingID,
				FIELD_SEPARATOR,
				mInstallationID,
				END_SYMBOL);
	}
}
