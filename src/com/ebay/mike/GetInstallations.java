package com.ebay.mike;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONArray;

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
	
//		String jsonText;
//		{
//			JSONObject j = new JSONObject();
//			j.put("name", "foo");
//			j.put("num", new Integer(100));
//			j.put("balance", new Double(1000.21));
//			j.put("is_vip", new Boolean(true));
//			j.put("nickname", null);
//
//			// StringWriter out = new StringWriter();
//			// obj.writeJSONString(out);
//			jsonText = j.toString();
//		}
//
//		System.out.print(jsonText);
//
//		JSONParser parser = new JSONParser();
//
//		try 
//		{
//			JSONObject j = (JSONObject) parser.parse(jsonText);
//			Long i = (Long) j.get("num");
//			String name = (String) j.get("name");
//			Double bal = (Double) j.get("balance");
//			Log("hi");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}

	}

	public static void main(String[] args)
	{
		GetInstallations x = new GetInstallations(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		JSONArray list = new JSONArray();
//		list.add("msg 1");
//		list.add("msg 2");
//		list.add("msg 3");
//	 
//		list.toJSONString();

		StringBuilder sb = new StringBuilder();
		List<Long> v = DB.getAllInstallationIDs(mDB);
		for (Long i : v) 
		{
			InstallationRecord r = new InstallationRecord(mDB, i);

			list.add(r.toJSON());

//			r.addFence (new FenceRecord(mDB, i, "testFence", -122.6, 45.3, 101.1, 3, "a fence url"));
			
//			String s = r.toString();
//			
//			InstallationRecord rr = new InstallationRecord(s);
//			sb.append(s);
//			sb.append("\n");
		}

		return list.toString();
	}
}
