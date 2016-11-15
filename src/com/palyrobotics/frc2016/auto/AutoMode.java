package com.palyrobotics.frc2016.auto;

import com.palyrobotics.frc2016.behavior.RoutineManager;
import com.palyrobotics.lib.util.routines.ParallelRoutine;
import com.palyrobotics.lib.util.routines.Routine;
import com.palyrobotics.lib.util.routines.SequentialRoutine;
import com.palyrobotics.lib.util.routines.TimedRoutine;
import com.palyrobotics.lib.util.routines.WaitRoutine;
import com.palyrobotics.lib.util.routines.DoUntilRoutine;

/**
 * Base AutoMode class, run buy the AutoModeExecuter, submits new routines to the RoutineManager
 * @author dardeshna
 *
 */
public abstract class AutoMode {
	
    private static int UPDATE_RATE = 20;  //in milliseconds
    
	protected RoutineManager routineManager;
    
    public abstract String toString();
    protected abstract void routine();
	
    /**
     * Sets the RoutineManager to be used
     * @param routineManager
     */
	public void setRoutineManager(RoutineManager routineManager) {
		this.routineManager = routineManager;
	}
	
	/**
	 * Called when the thread is run
	 */
    public void run() {
        if (routineManager != null)
        	routine();
    }

    /**
     * Called before the thread is killed
     */
    public void stop() {
    	routineManager.reset();
    }

    /**
     * Submits a new routine and prevents further execution of the AutoMode until the routine is done
     * @param routine
     */
    protected void execute(Routine routine) {
        routineManager.submitRoutine(routine);
        while (routineManager.isBusy()) {
            try {
                Thread.sleep(UPDATE_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
    /**
     * Returns a WaitRoutine of a specified length
     * @param seconds
     */
    protected Routine waitFor(double seconds) {
        return new WaitRoutine(seconds);
    }
    
    /**
     * Returns a TimedRoutine of a particular Routine  
     * @param routine a Routine or ParallelRoutine that terminates immediately
     * @param time
     * @return
     */
    protected Routine timed(Routine routine, double time) {
    	return new TimedRoutine(routine, time);
    }
    
    /**
     * Returns a ParallelRoutine of multiple Routines
     * @param routines
     * @return
     */
    protected Routine parallel(Routine... routines) {
    	return new ParallelRoutine(routines);
    }
    
    /**
     * Returns a SequentialRoutine of multiple Routines
     * @param routines
     * @return
     */
    protected Routine sequential(Routine... routines) {
    	return new SequentialRoutine(routines);
    }
    
    /**
     * Returns a DoUntilRoutine of a primary routine and one or more Routines
     * @param primaryRoutine a single Routine or ParallelRoutine that terminates immediately
     * @param routines
     * @return
     */
    protected Routine doUntil(Routine primaryRoutine, Routine... routines) {
    	if (primaryRoutine instanceof ParallelRoutine) {
    		((ParallelRoutine) primaryRoutine).cleanUpAtEnd();
    	}
    	return new DoUntilRoutine(primaryRoutine, routines);
    }
}
