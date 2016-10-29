package com.palyrobotics.lib.util.routines;

import edu.wpi.first.wpilibj.Timer;

/**
 * Waits for given time (in seconds)
 * @author Devin, Nihar
 *
 */
public class WaitAction extends Routine {
	
    private double timeout;
    private double mStartTime;
    
    /**
     * 
     * @param timeout Number of seconds to wait before finishing
     */
    public WaitAction(double timeout) {
        this.timeout = timeout;
    }

    @Override
    public void start() {
    	mStartTime = Timer.getFPGATimestamp();
    }
    
    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= mStartTime + timeout;
    }
    
    @Override
    public String getName() {
    	return "WaitAction"+timeout;
    }
}
