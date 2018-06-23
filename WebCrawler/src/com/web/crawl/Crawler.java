package com.web.crawl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.web.data.Link;
import com.web.data.LoadData;
import com.web.data.Site;
/**************************************************
 * 
 * @author John
 *	This class is the entry point for the WebCrawler program to handle single json files.
 .*  
 **************************************************/
public class Crawler implements Runnable{
	String fileName = "";
	HashMap<String, Site> pages = null;
	static final String Success = "Success";
	static final String Error = "Errors";
	static final String Skipped = "Skipped";
	HashSet sortList =new HashSet();

	public Crawler(String fileName) {
		this.fileName = fileName;
	}
	public static void main(String[] args) {

		File f = new File(args[0]);

		  if(!f.exists() || !f.isFile()){
			  System.out.println("Couldn't open ("+args[0]+")");
			  return;
		  }  
			  
			  Crawler crawl = new Crawler(args[0]);
		  
		crawl.run();
	}
  @Override
    public void run() {
		LoadData load = new LoadData();
		pages = load.loadSites(fileName);

		verifySites();

		displayResult(Success);
		displayResult(Skipped);
		displayResult(Error);
	}

	private void displayResult(String type) {
		boolean isFirst = true;
		sortList =new HashSet();
		System.out.println(type+":\n[");
		Iterator<String> iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			List<Link> links = site.getLinks();
			
			for(int x=0;x<links.size();x++) {
				Link link = (Link)links.get(x);
				if(type.equals(link.getResult())) {
					sortList.add(link.getLink());
				}
			}
		}
		List tempList = new ArrayList(sortList);
        Collections.sort(tempList);
        for(int x=0;x<tempList.size();x++) {
			if(!isFirst)
					System.out.print(",");
				isFirst = false;
				System.out.print("\""+(String)tempList.get(x)+"\"");
				isFirst = false;
			}
		
		System.out.println("]");
	}

	private void verifySites() {
		Iterator<String> iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			verifyLinks(site.getLinks());
		}

	}

	private void verifyLinks(List<Link> links) {
		for(int cnt=0;cnt<links.size();cnt++) {
			Link link = (Link)links.get(cnt);
			link.setResult(validLink(link));
		}
	}

	private String validLink(Link link) {
		if(pages.containsKey(link.getLink()))
			if(isDuplicate(link.getLink()))
				return Skipped;
		else
			return Success;
		return Error;
	}
	private boolean isDuplicate(String link) {
		Iterator<String> iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			for(int cnt=0;cnt<site.getLinks().size();cnt++) {
				Link linkComp = (Link)site.getLinks().get(cnt);
				if(linkComp.getLink().equals(link)&& "Success".equals(linkComp.getResult()))
					return true;
			}
		}
		return false;
	}
}
