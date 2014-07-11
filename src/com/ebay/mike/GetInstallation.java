package com.ebay.mike;

import java.sql.SQLException;
import java.util.List;

import com.ebay.mike.geodb.DB;
import com.ebay.mike.geodb.FenceRecord;
import com.ebay.mike.geodb.InstallationRecord;

/**
 * retrieve a named installation
 */
public class GetInstallation extends DBInterface
{
	/**
	 * "OpCode"				GetInstallation
	 * "InstallationGUID" 	installGuid to retrieve
	 */
	
	public GetInstallation(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		GetInstallation x = new GetInstallation(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		String installGuid = mParams.get("InstallationGUID");
		InstallationRecord out = null;
		
		List<Long> v = DB.getAllInstallationIDs(mDB);
		for (Long i : v) 
		{
			InstallationRecord r = new InstallationRecord(mDB, i);
			if (r.mGuid.equals(installGuid))
			{
				out = r;
	
				if (r.mFences.size() == 0)
					r.addFence (new FenceRecord(mDB, i, "testFence", -122.6, 45.3, 101.1, 3, "a fence url"));
			}
		}

		return out.toString();
	}
}
