package com.ebay.mike;

import java.sql.SQLException;
import java.util.List;

import com.ebay.mike.geodb.DB;
import com.ebay.mike.geodb.InstallationRecord;

/**
 * retrieve all known installations
 */
public class GetInstallations extends DBInterface
{
	/**
	 * app does a GET to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = GetInstallations we get here
	 * we output all known Installation records, stringified
	 * 
	 * no args
	 */
	
	public GetInstallations(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		GetInstallations x = new GetInstallations(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		StringBuilder sb = new StringBuilder();
		List<Long> v = DB.getAllInstallationIDs(mDB);
		for (Long i : v) 
		{
			InstallationRecord r = new InstallationRecord(mDB, i);
			sb.append(r.toString());
			sb.append("\n");
		}

		return sb.toString();
	}
}
