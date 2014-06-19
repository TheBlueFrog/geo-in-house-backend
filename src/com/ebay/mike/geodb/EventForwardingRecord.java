package com.ebay.mike.geodb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	private List<EventForwardingTargetRecord> mTargets;
	private Connection mDB;
	
		
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
	
	public EventForwardingRecord(Connection db, ResultSet rs) throws SQLException 
	{
		mDB = db;
		
		mID = rs.getLong(1);
		mFenceID = rs.getLong(2);
		mInstallationID = rs.getLong(3);
		mIncomingEventType = rs.getInt(4);

		mTargets = getForwardingTargets(mDB);
	}

	public EventForwardingRecord(ResultSet rs) throws SQLException 
	{
		mID = rs.getLong(1);
		mFenceID = rs.getLong(2);
		mInstallationID = rs.getLong(3);
		mIncomingEventType = rs.getInt(4);
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

	public List<EventForwardingTargetRecord> getForwardingTargets (Connection db) throws SQLException
	{
		List<EventForwardingTargetRecord> v = DB.getEventForwardingTargets(db, mFenceID);
		return v;
	}
}
