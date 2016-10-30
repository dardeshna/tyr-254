package com.palyrobotics.lib.util.routines;

import java.util.ArrayList;

import com.team254.lib.util.Subsystem;

/**
 * Superclass for a Routine - automated control of the robot for any game period <br />
 * Routines start with {@link Routine#start()}. 
 * Then {@link Routine#update()} is called on loop.
 * When it reaches completion based on {@link Routine#isFinished()} 
 * it calls {@link Routine#cleanup()} one time. 
 * When terminated without completing, calls {@link Routine#cancel()} one time. 
 * <br /> <br />
 * Subsystems that the routine needs should be added to {@link Routine#requiredSubsystems}
 * using {@link Routine#requires(Subsystem...)}
 * <br /> <br />
 * By default, all of the linked methods are empty and {@link Routine#isFinished()} returns true. 
 * Override the appropriate methods, and must have a {@link Routine#getName()} method
 * @author Devin, Nihar
 *
 */
public abstract class Routine {

	public Subsystem[] requiredSubsystems;

	/**
	 * {@link Routine#requiredSubsystems} becomes this list of subsystems
	 * <br />
	 * Used to avoid multiple routines controlling a subsystem
	 * 
	 * @param subsystems
	 *            all the subsystems that are required
	 */
	public void requires(Subsystem... subsystems) {
		this.requiredSubsystems = subsystems;
	}

	/**
	 * Called when the routine is started
	 */
	public void start() { }

	/**
	 * Called every update cycle
	 */
	public void update() { }

	/**
	 * Returns whether the routine is finished
	 * By default returns true immediately
	 * @return whether the routine is finished
	 */
	public boolean isFinished() { return true; }

	/**
	 * Called when routine naturally finishes
	 */
	public void cleanup() { }

	/**
	 * Called when routine is manually canceled
	 */
	public void cancel() { }

	/**
	 * Gives a string name for the routine or action
	 * 
	 * @return String name of the routine
	 */
	public abstract String getName();

	/**
	 * Helper method to find the common required subsystems of multiple routines
	 * 
	 * @param routines
	 *            the routines to check
	 * @return the required subsystems all require
	 */
	public static Subsystem[] subsystems(Routine[] routines) {
		ArrayList<Subsystem> requiredSubsystemsList = new ArrayList<Subsystem>();
		for (Routine r : routines) {
			for (Subsystem s : r.requiredSubsystems) {
				for (Subsystem t : requiredSubsystemsList) {
					if (s.equals(t)) {
						try {
							throw new Exception("Two routines require the same subsystem!" + s.getName());
						} catch (Exception e) {
							System.out.println(e.toString());
							e.printStackTrace();
						}
					} else {
						requiredSubsystemsList.add(s);
					}
				}
			}
		}
		return (Subsystem[]) requiredSubsystemsList.toArray();
	}

}
