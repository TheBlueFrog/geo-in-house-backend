package com.ebay.mike.abstractdb;

import java.util.List;

public class AbstractEventForwardingRecord extends AbstractRecord 
{
	public long mID;
	public long mFenceID;
	public long mInstallationID;
	
	/** event type we match */
	public int mIncomingEventType;
	
	/** installations to forward to */
	protected List<AbstractEventForwardingTargetRecord> mTargets;
	
	protected AbstractEventForwardingRecord() 
	{
	}
	
	protected AbstractEventForwardingRecord(String s)
	{
		int start = s.indexOf(START_SYMBOL);
		int end = s.indexOf(END_SYMBOL);
		String[] a = s.substring(start+1, end).split("[" + FIELD_SEPARATOR + "]");
		
		mID = Long.parseLong(a[0]);
		mFenceID = Long.parseLong(a[1]);
		mInstallationID = Long.parseLong(a[2]);
		mIncomingEventType = Integer.parseInt(a[3]);
	}

	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append (START_SYMBOL);
//		for (EventForwardingTargetRecord t : mTargets)
//			sb.append(DB.getInstallationGuid(mDB, t.mInstallationID));
//		sb.replace(sb.length() - 1, sb.length(), "");
		sb.append(END_SYMBOL);
		
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
