package com.ebay.mike.abstractdb;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

	@Override
	public String toString() 
	{
		return toJSON().toString();
	}

	public AbstractEventForwardingRecord(JSONObject j)
	{
		mID = j.getLong("id");
		mFenceID = j.getLong("FenceID");
		mInstallationID = j.getLong("InstallationID");
		mIncomingEventType = j.getInt("IncomingEventType");
	}
	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		j.put("id", mID);
		j.put("FenceID", mFenceID);
		j.put("InstallationID", mInstallationID);
		j.put("IncomingEventType", mIncomingEventType);

		JSONArray a = new JSONArray();
		for (AbstractEventForwardingTargetRecord t : mTargets)
			a.put(t.toJSON());
		
		String jsonText = j.toString();
		return j;
	}

}
