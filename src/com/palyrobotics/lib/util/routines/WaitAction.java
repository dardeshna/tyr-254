package com.palyrobotics.lib.util.routines;

import edu.wpi.first.wpilibj.Timer;

public class WaitAction extends Action {
	
    private double timeout;
    private double startTime;
    
    public WaitAction(double timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= startTime + timeout;
    }
    
    @Override
    public void update() {};
}
