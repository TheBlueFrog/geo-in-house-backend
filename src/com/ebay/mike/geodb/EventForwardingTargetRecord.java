package com.ebay.mike.geodb;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ebay.mike.abstractdb.AbstractEventForwardingTargetRecord;

/**
	CREATE TABLE EvemtForwardingTargets
	    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
		EvenForwardingID INTEGER,
		InstallationID INTEGER
		);
 
 */
public class EventForwardingTargetRecord extends AbstractEventForwardingTargetRecord
{
	public EventForwardingTargetRecord(ResultSet rs) throws SQLException 
	{
		mID = rs.getLong(1);
		mEvenForwardingID = rs.getLong(2);
		mInstallationID = rs.getLong(3);
	}
}
