package com.palyrobotics.lib.util.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.palyrobotics.lib.util.Requirable;
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

	public ArrayList<Requirable> required;

	/**
	 * {@link Routine#requiredSubsystems} becomes this list of subsystems
	 * <br />
	 * Used to avoid multiple routines controlling a subsystem
	 * 
	 * @param subsystems
	 *            all the subsystems that are required
	 */
	public void requires(Requirable... required) {
		this.required = new ArrayList<Requirable>(Arrays.asList(required));
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
	public void cancel() { cleanup(); }

	/**
	 * Gives a string name for the routine or action
	 * 
	 * @return String name of the routine
	 */
	public abstract String getName();

	/**
	 * Helper method to find the common required objects of multiple routines
	 * 
	 * @param routines the routines to check
	 * @param safe whether it is okay for composing routines to have the same subsystems
	 * @return the required subsystems all require
	 */
	public static ArrayList<Requirable> required(Routine[] routines, boolean safe) {
		ArrayList<Requirable> requiredList = new ArrayList<Requirable>();
		for (Routine r : routines) {
			required(r.required, requiredList, safe);
		}
		return requiredList;
	}
	
	public static ArrayList<Requirable> required(ArrayList<Requirable> requiredA, ArrayList<Requirable> requiredB, boolean safe) {
		
		//TODO: Logic is probably quite messed up
		
		ArrayList<Requirable> requiredList = new ArrayList<Requirable>();
			for (Requirable a : requiredA) {
				for (Requirable b : requiredB) {
					if (b.getRequirables() != null) {
						requiredList = required(requiredList, b.getRequirables(), safe);
					}
					if (a.equals(b)) {
						if (!safe) {
							try {
								throw new Exception("Two routines require the same requirables!");
							} catch (Exception e) {
								System.out.println(e.toString());
								e.printStackTrace();
							}
						}
					} else {
						requiredList.add(a);
					}
				}
			}
		return requiredList;
		
	}

}
