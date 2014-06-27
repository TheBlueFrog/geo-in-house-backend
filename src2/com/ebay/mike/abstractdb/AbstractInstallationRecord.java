package com.ebay.mike.abstractdb;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;




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
	public List<AbstractFenceRecord> mFences = new ArrayList<AbstractFenceRecord>();


	protected AbstractInstallationRecord()
	{
	}
	
	/** construct from string
	 * 
	 * @param s
	 */
	public AbstractInstallationRecord(String s)
	{
	}
	
	public AbstractInstallationRecord(JSONObject j)
	{
		mID = j.getLong("id");
		mGuid = j.getString("Guid");
		mDisplayName = j.getString("DisplayName");
		mGCMRegistrationID = j.getString("GCMRegistrationID");
		
		JSONArray a = (JSONArray) j.get("Fences");
		for (int i = 0; i < a.length(); ++i)
			mFences.add(new AbstractFenceRecord((JSONObject) a.get(i)));
	}

	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		j.put("id", mID);
		j.put("Guid", mGuid);
		j.put("DisplayName", mDisplayName);
		j.put("GCMRegistrationID", mGCMRegistrationID);

		JSONArray fences = new JSONArray();
		for (AbstractFenceRecord f : mFences)
			fences.put(f.toJSON());
		
		j.put("Fences", fences);
		
		String jsonText = j.toString();
		return j;
	}
	
	@Override
	public String toString()
	{
		return toJSON().toString();
	}
}
