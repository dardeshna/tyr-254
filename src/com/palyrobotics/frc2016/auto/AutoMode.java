package com.palyrobotics.frc2016.auto;

import com.palyrobotics.lib.util.routines.*;

/**
 * Provides wrapper methods for waiting time and running routines
 * @author Devin
 *
 */
public abstract class AutoMode extends AutoModeBase {

    protected void wait(double seconds) {
        execute(new WaitAction(seconds));
    }
    
    protected Routine timed(Routine action, double time) {
    	return new TimedAction(action, time);
    }
    
    protected Routine parallel(Routine... routines) {
    	return new ParallelRoutine(routines);
    }
    
    protected Routine sequential(Routine... routines) {
    	return new SequentialRoutine(routines);
    }
}
