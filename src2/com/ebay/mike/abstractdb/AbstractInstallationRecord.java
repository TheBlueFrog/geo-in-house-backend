package com.ebay.mike.abstractdb;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



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
	public List<AbstractFenceRecord> mFences;


	protected AbstractInstallationRecord()
	{
	}
	
	/** construct from string
	 * 
	 * @param s
	 */
	public AbstractInstallationRecord(String s)
	{
		JSONParser parser = new JSONParser();

		try 
		{
			JSONObject j = (JSONObject) parser.parse(s);
			mID = (Long) j.get("id");
			mGuid = (String) j.get("Guid");
			mDisplayName = (String) j.get("DisplayName");
			mGCMRegistrationID = (String) j.get("GCMRegistrationID");
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		
//		int start = s.indexOf(START_SYMBOL);
//		int end = s.indexOf(END_SYMBOL);
//		String[] a = s.substring(start+1, end).split("[" + FIELD_SEPARATOR + "]");
//		
//		mID = Long.parseLong(a[0]);
//		mGuid = a[1];
//		mDisplayName = a[2];
//		mGCMRegistrationID = a[3];
	}
	
	public JSONObject toJSON() 
	{
		
<<<<<<< HEAD
		String jsonText;
		{
			JSONObject j = new JSONObject();
			j.put("id", mID);
			j.put("Guid", mGuid);
			j.put("DisplayName", mDisplayName);
			j.put("GCMRegistrationID", mGCMRegistrationID);

			JSONArray fences = new JSONArray();
			for (AbstractFenceRecord f : mFences)
				fences.add(f.toJSON());
			
			j.put("Fences", fences);
			
			// StringWriter out = new StringWriter();
			// obj.writeJSONString(out);
			jsonText = j.toString();
			return j;
		}

//		return String.format("%s%d%s%s%s%s%s%s%s",
//				START_SYMBOL,
//				mID,
//				FIELD_SEPARATOR,
//				mGuid,
//				FIELD_SEPARATOR,
//				mDisplayName, 
//				FIELD_SEPARATOR,
//				mGCMRegistrationID,
//				FIELD_SEPARATOR,
//				fences.toString(),
//				END_SYMBOL);
=======
		return String.format("%s%d%s%s%s%s%s%s%s%s%s",
				START_SYMBOL,
				mID,
				FIELD_SEPARATOR,
				mGuid,
				FIELD_SEPARATOR,
				mDisplayName, 
				FIELD_SEPARATOR,
				mGCMRegistrationID,
				FIELD_SEPARATOR,
				fences.toString(),
				END_SYMBOL);
>>>>>>> 70417c0c7acc174b5926012ddd70924d9dcc4abc
	}
}
