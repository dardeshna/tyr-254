package com.palyrobotics.lib.util.routines;

/**
 * Lightweight subclass of Routine
 * @author dardeshna
 *
 */
public class Action extends Routine {
	
	@Override
    public void update() { }
    
	@Override
	public boolean isFinished() {return true;}

	@Override
	public void start() { }

	@Override
	public void cleanup() { }

	@Override
	public void cancel() { }

	@Override
	public String getName() {
		return "GenericAction";
	}

    
}
