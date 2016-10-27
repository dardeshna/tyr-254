package com.palyrobotics.frc2016.routines;

import java.util.ArrayList;

import com.team254.lib.util.Subsystem;

public abstract class Routine {
    
	public Subsystem[] requiredSubsystems;
	
	public void requires(Subsystem... subsystems) {
		this.requiredSubsystems = subsystems;
	}
	
	public static Subsystem[] checkSubsystems(ArrayList<Routine> routines) throws Exception {
		ArrayList<Subsystem> requiredSubsystemsList = new ArrayList<Subsystem>();
        for (Routine r : routines) {
        	for (Subsystem s: r.requiredSubsystems) {
        		for (Subsystem t: requiredSubsystemsList) {
        			if (s.equals(t)) {
        				throw new Exception("Two routines require the same subsystem!");
        			}
        			else {
        				requiredSubsystemsList.add(s);
        			}
        		}
        	}
        }
        return (Subsystem[]) requiredSubsystemsList.toArray();
	}
	
    // Called to start a routine
    public abstract void start();

    public abstract void update();
    
    public abstract boolean isFinished();
    
    public abstract void cleanup();
    
    public abstract void cancel();

    public abstract String getName();
}
