package com.ebay.mike.geodb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ebay.mike.abstractdb.AbstractEventForwardingRecord;
import com.ebay.mike.abstractdb.AbstractEventForwardingTargetRecord;

/**
	CREATE TABLE EventForwardings
	    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	    FenceID INTEGER,
	    InstallationID INTEGER,
	    IncomingEventType INTEGER);
 
 */
public class EventForwardingRecord extends AbstractEventForwardingRecord
{
	protected Connection mDB;

	public EventForwardingRecord(Connection db, ResultSet rs) throws SQLException 
	{
		super();
		
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

	public List<AbstractEventForwardingTargetRecord> getForwardingTargets (Connection db) throws SQLException
	{
		List<AbstractEventForwardingTargetRecord> v = DB.getEventForwardingTargets(db, mFenceID);
		return v;
	}
}
