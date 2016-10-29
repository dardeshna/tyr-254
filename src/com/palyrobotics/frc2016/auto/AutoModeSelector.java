package com.palyrobotics.frc2016.auto;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import com.palyrobotics.frc2016.auto.modes.DoNothingAutoMode;
import com.palyrobotics.lib.util.Dashboard;

public class AutoModeSelector {
	private static AutoModeSelector instance = null;
	private ArrayList<AutoMode> autoModes = new ArrayList<AutoMode>();
	/**
	 * 0 - none
	 * 1 - distance low bar auto
	 * 2 - distance bd auto
	 * 3 - timer low bar auto
	 * 4 - timer bd auto
	 */
	int selectedIndex = 4;
	public static AutoModeSelector getInstance() {
		if (instance == null) {
			instance = new AutoModeSelector();
		}
		return instance;
	}

	/**
	 * Add an AutoMode to list to choose from
	 * @param auto AutoMode to add
	 */
	public void registerAutonomous(AutoMode auto) {
		autoModes.add(auto);
	}

	public AutoModeSelector() {
		registerAutonomous(new DoNothingAutoMode());
	}

	/**
	 * Get the currently selected AutoMode
	 * @return AutoMode currently selected
	 */
	public AutoMode getAutoMode() {
		return autoModes.get(selectedIndex);
	}

	/**
	 * Get the AutoMode at specified index
	 * @param index index of desired AutoMode
	 * @return AutoMode at specified index
	 */
	public AutoMode getAutoMode(int index) {
		return autoModes.get(index);
	}

	/**
	 * Gets the names of all registered AutoModes
	 * @return ArrayList of AutoModes string name
	 * @see AutoMode#toString()
	 */
	public ArrayList<String> getAutoModeList() {
		ArrayList<String> list = new ArrayList<String>();
		for (AutoMode autoMode : autoModes) {
			list.add(autoMode.toString());
		}
		return list;
	}

	public JSONArray getAutoModeJSONList() {
		JSONArray list = new JSONArray();
		list.addAll(getAutoModeList());
		return list;
	}

	/**
	 * Attempt to set
	 * @return false if unable to find appropriate AutoMode
	 * @see AutoMode#toString()
	 */
	public boolean setAutoModeByName(String name) {
		int numOccurrences = 0;
		int index = -1;
		for(int i=0; i<autoModes.size(); i++) {
			if(autoModes.get(i).toString() == name) {
				numOccurrences++;
				index = i;
			}
		}
		if(numOccurrences == 1) {
			setAutoModeByIndex(index);
			return true;
		} else if(numOccurrences == 0) {
			System.out.println("Couldn't find AutoMode "+name);
		} else {
			System.out.println("Found multiple AutoModes "+name);
		}
		System.err.println("Didn't select AutoMode");
		return false;
	}

	/**
	 * Called during disabled in order to access dashbord and set auto mode
	 * @return false if unable to set automode
	 */
	public boolean setFromDashboard() {
		String selection = Dashboard.getInstance().getSelectedAutoMode();
		if(!setAutoModeByName(selection)) {
			Dashboard.getInstance().getTable().putString("autopath", getAutoMode().toString());
			return false;
		}
		return true;
	}

	private void setAutoModeByIndex(int which) {
		if (which < 0 || which >= autoModes.size()) {
			which = 0;
		}
		selectedIndex = which;
		System.out.println("Selected AutoMode "+autoModes.get(selectedIndex).toString());
	}

}
