package com.ebay.mike.abstractdb;

import org.json.JSONObject;


public class AbstractFenceRecord extends AbstractRecord 
{
	public long mID;
	public long mInstallationID;
	public String mGuid;
	public String mDisplayName;
	public double mLatitude;
	public double mLongitude;
	public double mRadius;
	public int mEvents;
	public String mURI;

	protected AbstractFenceRecord() 
	{
	}

	@Override
	public String toString() 
	{
		return toJSON().toString();
	}

	public AbstractFenceRecord(JSONObject j)
	{
		mID = j.getLong("id");
		mInstallationID = j.getLong("InstallationID");
		mGuid = j.getString("Guid");
		mDisplayName = j.getString("DisplayName");
		mLatitude = j.getDouble("Latitude");
		mLongitude = j.getDouble("Longitude");
		mRadius = j.getDouble("Radius");
		mEvents = j.getInt("Events");
		mURI = j.getString("URI");
	}

	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		j.put("id", mID);
		j.put("InstallationID", mInstallationID);
		j.put("Guid", mGuid);
		j.put("DisplayName", mDisplayName);
		j.put("Latitude", mLatitude);
		j.put("Longitude", mLongitude);
		j.put("Radius", mRadius);
		j.put("Events", mEvents);
		j.put("URI", mURI);
		
		String jsonText = j.toString();
		return j;
	}
}
