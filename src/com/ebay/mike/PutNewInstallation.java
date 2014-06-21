package com.ebay.mike;

import java.sql.SQLException;

import com.ebay.mike.geodb.DB;
import com.ebay.mike.geodb.InstallationRecord;

/**
 * add a new installation to the DB
 */
public class PutNewInstallation extends DBInterface
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = PutNewInstallation we get here
	 * we return the toString of the new installation record

	 * args are a map
	 * 
	 *  params.put("InstallationGUID", installGuid);
		params.put("DisplayName", displayName);
		params.put("GCMRegistrationID", gcmRegistrationID);
	 */
	
	public PutNewInstallation(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		PutNewInstallation x = new PutNewInstallation(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		long id = DB.getGuidID(mDB, mParams.get("InstallationGUID"));
		if (id == -1)
		{
			InstallationRecord r = new InstallationRecord(mDB, 
					mParams.get("InstallationGUID"), 
					mParams.get("DisplayName"),
					mParams.get("GCMRegistrationID"));
	
			return r.toString();
		}
		else
		{
			return "Error, InstallationGUID exists " + mParams.get("InstallationGUID");
		}
	}
	
}
