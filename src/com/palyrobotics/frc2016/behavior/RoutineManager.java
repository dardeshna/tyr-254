package com.palyrobotics.frc2016.behavior;

import com.palyrobotics.lib.util.routines.Routine;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Tappable;

public class RoutineManager implements Tappable {

	private Routine currentRoutine = null;

	public synchronized void submitRoutine(Routine newRoutine) {
		if (!newRoutine.getClass().equals(currentRoutine.getClass())) {
			setNewRoutine(newRoutine);
		}	
	}
	
	public synchronized void update() {
		if (currentRoutine != null) {
			currentRoutine.update();
			if (currentRoutine.isFinished()) {
				currentRoutine.cleanup();
				setNewRoutine(null);
			}
		}
	}
	
	public synchronized void reset() {
		if(currentRoutine != null) {
			currentRoutine.cancel();
		}
		setNewRoutine(null);	
	}
	
	public boolean isBusy() {
		return !(currentRoutine == null);
	}	

	private void setNewRoutine(Routine newRoutine) {
		if ((newRoutine != currentRoutine) && (currentRoutine != null)) {
			currentRoutine.cancel();
		}
		if ((newRoutine != currentRoutine) && (newRoutine != null)) {
			newRoutine.start();
		}
		currentRoutine = newRoutine;

	}
	
	@Override
	public void getState(StateHolder states) {
		states.put("mode", currentRoutine != null ? currentRoutine.getName() : "---");
	}

	@Override
	public String getName() {
		return "RoutineManager";
	}
}