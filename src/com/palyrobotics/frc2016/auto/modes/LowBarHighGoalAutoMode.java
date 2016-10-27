package com.palyrobotics.frc2016.auto.modes;

import com.palyrobotics.frc2016.auto.AutoMode;
import com.palyrobotics.frc2016.auto.AutoModeEndedException;

/**
 * Goes under the low bar then shoots a high goal
 */
public class LowBarHighGoalAutoMode extends AutoMode {
	public static final double mCompressorWaitTime = 3;
	
	@Override
	protected void routine() throws AutoModeEndedException {
		/*
		
		waitTime(mCompressorWaitTime); //Waits for compressor
		ArrayList<Routine> crossLowBar = new ArrayList<Routine>(2);
		crossLowBar.add(new EncoderDriveRoutine(Constants.kLowBarDistance));
		crossLowBar.add(new ShooterDown());
		ArrayList<Routine> prepareGoal = new ArrayList<Routine>(2);
		prepareGoal.add(new ShooterUp());
		prepareGoal.add(new IntakeBall(1.0, WantedIntakeState.INTAKING));
		
		updateRoutine(new ParallelRoutine(crossLowBar));		
		updateRoutine(new AutoAlignmentRoutine());
		updateRoutine(new ParallelRoutine(prepareGoal));
		updateRoutine(new ShootRoutine());
		waitTime(0.5);

		updateRoutine(new ShooterDown());
		
		*/
		
	}

	
	@Override
	public String toString() {
		return "LowBar_HighGoal";
	}


	@Override
	public void prestart() {
		// TODO Auto-generated method stub
		
	}
}
