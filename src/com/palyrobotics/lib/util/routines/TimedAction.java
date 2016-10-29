package com.palyrobotics.lib.util.routines;

import edu.wpi.first.wpilibj.Timer;

public class TimedAction extends Action {

    private double timeout;
    private double startTime;
    
    private Action action;

    public TimedAction(Action action, double timeout) {
        this.action = action;
        requiredSubsystems = action.requiredSubsystems;
    }
    
    @Override
    public boolean isFinished() {
    	return Timer.getFPGATimestamp() >= startTime + timeout;
    }
    
    @Override
    public void update() {
    	action.update();
    }

	@Override
	public String getName() {
		return "Timed"+action.getName();
	}
	
}
