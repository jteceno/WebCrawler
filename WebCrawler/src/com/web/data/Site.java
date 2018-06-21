package com.web.data;

import java.util.ArrayList;
import java.util.List;

public class Site {
	String address = "";
	
	List<Link> links = new ArrayList<Link>();
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public void addLinks(Link link) {
		links.add(link);
	}

}
