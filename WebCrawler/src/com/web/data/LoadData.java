package com.web.data;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoadData {
	HashMap<String, Site> rtnMap = new HashMap<String, Site>();
	   public HashMap<String, Site> loadSites(String file) {
	        JSONParser parser = new JSONParser();
	 
	        try {
	 
	            Object obj = parser.parse(new FileReader(file));
	 
	            JSONObject jsonObject = (JSONObject) obj;
	            JSONArray pages = (JSONArray)jsonObject.get("pages");
	            Iterator<JSONObject> i = pages.iterator();

	            while (i.hasNext()) {
	                JSONObject jObj = (JSONObject) i.next();
	                
	                String address = (String)jObj.get("address");
	                Site site = new Site();
	                site.setAddress(address);
//	                System.out.println("Address:"+address);
	                JSONArray links = (JSONArray)jObj.get("links");
	                Iterator<String> linkIter = links.iterator();
	                while(linkIter.hasNext()) {
	                	Link link = new Link((String) linkIter.next());
	                	site.addLinks(link);
	                }
	                rtnMap.put(address, site);
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return rtnMap;
	    }
}
