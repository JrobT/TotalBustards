package com.mycompany.app;

import java.io.Serializable;

public class BusStop implements Serializable {
	private LocXY loc;
	private String name;
	
	public BusStop(String name, LocXY loc) {
		this.name = name;
		this.loc = loc;
	}

	public LocXY getLoc() { return loc; }
	public String getName() { return name; }
}
