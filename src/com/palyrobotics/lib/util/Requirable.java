package com.palyrobotics.lib.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an object that must be required by a Routine.  May contain other Requirables itself.
 * @author dardeshna
 *
 */
public class Requirable {

	private ArrayList<Requirable> requirables = null;
	
	public Requirable() {
		
	}
	
	public Requirable(Requirable... required) {
		this.requirables = new ArrayList<Requirable>(Arrays.asList(required));
	}
	
	public ArrayList<Requirable> getRequirables() {
		return requirables;
	}
	
}
