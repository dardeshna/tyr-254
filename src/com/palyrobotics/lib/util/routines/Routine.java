package com.palyrobotics.lib.util.routines;

import java.util.ArrayList;

import com.team254.lib.util.Subsystem;

public abstract class Routine {
    
	public Subsystem[] requiredSubsystems;
	
	/**
	 * Adds one or more subsystems to the required list to avoid multiple routines controlling a subsystem
	 * @param subsystems the one or more subsystems
	 */
	public void requires(Subsystem... subsystems) {
		this.requiredSubsystems = subsystems;
	}
	
	/**
	 * Called when the routine is started
	 */
    public abstract void start();

    /**
     * Called every update cycle
     */
    public abstract void update();
    
    /**
     * Returns whether the routine is finished
     * @return whether the routine is finished
     */
    public abstract boolean isFinished();
    
    /**
     * Called when routine naturally finishes
     */
    public abstract void cleanup();
    
    /**
     * Called when routine is manually canceled
     */
    public abstract void cancel();

    public String getName() {
    	return "GenericRoutine";
    }
    
    /**
     * Helper method to return the common subsystems of two or more routines
     * @param routines the routines
     * @return the common subsystems
     */
    public static Subsystem[] subsystems(Routine[] routines) {
		ArrayList<Subsystem> requiredSubsystemsList = new ArrayList<Subsystem>();
        for (Routine r : routines) {
        	for (Subsystem s: r.requiredSubsystems) {
        		for (Subsystem t: requiredSubsystemsList) {
        			if (s.equals(t)) {
        				try {
							throw new Exception("Two routines require the same subsystem!");
						} catch (Exception e) {
							e.printStackTrace();
						}
        			}
        			else {
        				requiredSubsystemsList.add(s);
        			}
        		}
        	}
        }
        return (Subsystem[]) requiredSubsystemsList.toArray();
	}
    
}
