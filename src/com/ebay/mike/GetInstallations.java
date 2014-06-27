package com.ebay.mike;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

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
<<<<<<< HEAD
	
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

=======
		
//		  JSONObject obj=new JSONObject();
//		  obj.put("name","foo");
//		  obj.put("num",new Integer(100));
//		  obj.put("balance",new Double(1000.21));
//		  obj.put("is_vip",new Boolean(true));
//		  obj.put("nickname",null);
//		  String s = obj.toString();

		  
		  Map obj=new LinkedHashMap();
		  obj.put("name","foo");
		  obj.put("num",new Integer(100));
		  obj.put("balance",new Double(1000.21));
		  obj.put("is_vip",new Boolean(true));
		  obj.put("nickname",null);
		  String jsonText = JSONValue.toJSONString(obj);

		  
		  Object obj2=JSONValue.parse(jsonText);
		  JSONArray array=(JSONArray)obj2;

		  array.get(0);
>>>>>>> 70417c0c7acc174b5926012ddd70924d9dcc4abc
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
			
<<<<<<< HEAD
//			String s = r.toString();
//			
//			InstallationRecord rr = new InstallationRecord(s);
//			sb.append(s);
//			sb.append("\n");
=======
			if (r.mFences.size() == 0)
				r.addFence (new FenceRecord(mDB, i, "testFence", -122.6, 45.3, 101.1, 3, "a fence url"));
			
			sb.append(r.toString());
			sb.append("\n");
>>>>>>> 70417c0c7acc174b5926012ddd70924d9dcc4abc
		}

		return list.toString();
	}
}
