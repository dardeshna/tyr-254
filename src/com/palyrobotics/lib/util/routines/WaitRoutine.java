package com.palyrobotics.lib.util.routines;

import edu.wpi.first.wpilibj.Timer;

public class WaitRoutine extends Routine {
	
    private double timeout;
    private double startTime;
    
    public WaitRoutine(double timeout) {
        this.timeout = timeout;
    }
    
    @Override
    public void start() {
    	startTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= startTime + timeout;
    }
    
    @Override
    public void update() {}

	@Override
	public String getName() {
		return "WaitRoutine";
	};
}
