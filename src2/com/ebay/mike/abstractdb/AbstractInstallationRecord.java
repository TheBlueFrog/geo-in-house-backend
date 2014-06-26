package com.ebay.mike.abstractdb;

import java.util.List;


/**
	class to wrap table row
 */
public class AbstractInstallationRecord extends AbstractRecord 
{
	/** record id */
	public long mID = -1;

	/** GUID of the device installation represent */
	public String mGuid;
	
	public String mDisplayName;
	
	/** Google Cloud Messaging registration ID */
	public String mGCMRegistrationID;

	/** Fences of this installation */
	public List<AbstractFenceRecord> mFences;


	protected AbstractInstallationRecord()
	{
	}
	
	/** construct from string
	 * 
	 * @param s
	 */
	public AbstractInstallationRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split("[" + FIELD_SEPARATOR + "]");
		
		mID = Long.parseLong(a[0]);
		mGuid = a[1];
		mDisplayName = a[2];
		mGCMRegistrationID = a[3];
	}
	
	public String toString() 
	{
		StringBuilder fences = new StringBuilder();
		for (AbstractFenceRecord f : mFences)
			fences.append(f.toString());
		
		return String.format("%s%d%s%s%s%s%s%s%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mGuid,
				FIELD_SEPARATOR,
				mDisplayName, 
				FIELD_SEPARATOR,
				mGCMRegistrationID,
				FIELD_SEPARATOR,
				fences.toString(),
				END_SYMBOL);
	}
}
