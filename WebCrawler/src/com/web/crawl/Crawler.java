package com.web.crawl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.web.data.Link;
import com.web.data.LoadData;
import com.web.data.Site;

public class Crawler implements Runnable{
	String fileName = "";
	HashMap pages = null;
	static final String Success = "Success";
	static final String Error = "Error";
	static final String Skipped = "Skipped";
	
	public Crawler(String fileName) {
		this.fileName = fileName;
	}
  @Override
    public void run() {
		LoadData load = new LoadData();
		pages = load.loadSites(fileName);
//		pages = load.loadSites("C:\\Challenges\\WebCrawler\\data\\internet2.json");
		verifySites();
		showSuccess();
		showSkipped();
		showErrors();
	}

	private void showErrors() {
		boolean isFirst = true;
		System.out.println("Errors:\n[");
		Iterator iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			List links = site.getLinks();
			for(int x=0;x<links.size();x++) {
				Link link = (Link)links.get(x);
				if(Error.equals(link.getResult())) {
					if(!isFirst)
						System.out.print(",");
					isFirst = false;
					System.out.print("\""+link.getLink()+"\"");
					isFirst = false;
				}
			}
		}
		System.out.println("]");
	}
	private void showSuccess() {
		boolean isFirst = true;
		System.out.println("Success:\n[");
		Iterator iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			List links = site.getLinks();
			for(int x=0;x<links.size();x++) {
				Link link = (Link)links.get(x);
				if(Success.equals(link.getResult())) {
					if(!isFirst)
						System.out.print(",");
					isFirst = false;
					System.out.print("\""+link.getLink()+"\"");
					isFirst = false;
				}
			}
		}
		System.out.println("]");
	}
	private void showSkipped() {
		boolean isFirst = true;
		System.out.println("Skipped:\n[");
		Iterator iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			List links = site.getLinks();
			for(int x=0;x<links.size();x++) {
				Link link = (Link)links.get(x);
				if(Skipped.equals(link.getResult())) {
					if(!isFirst)
						System.out.print(",");
					isFirst = false;
					System.out.print("\""+link.getLink()+"\"");
				}
			}
		}
		System.out.println("]");
	}
	private void verifySites() {
		Iterator iter = pages.keySet().iterator();
		while(iter.hasNext()) {
			String address = (String)iter.next();
			Site site = (Site)pages.get(address);
			verifyLinks(site.getLinks());
//			System.out.println("Address:"+address);
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
		Iterator iter = pages.keySet().iterator();
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
