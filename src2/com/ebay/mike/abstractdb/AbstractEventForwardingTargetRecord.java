package com.ebay.mike.abstractdb;


public class AbstractEventForwardingTargetRecord extends AbstractRecord 
{
	public long mID;
	
	/** record ID of EventForwardingRecord we belong to */
	public long mEvenForwardingID;

	/** installation to forward event to */
	public long mInstallationID;
	
	protected AbstractEventForwardingTargetRecord()
	{
	}
	
	public AbstractEventForwardingTargetRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split("[" + FIELD_SEPARATOR + "]");
		
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