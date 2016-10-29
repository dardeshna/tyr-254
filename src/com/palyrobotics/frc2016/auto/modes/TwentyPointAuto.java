package com.palyrobotics.frc2016.auto.modes;

import com.palyrobotics.frc2016.Constants;
import com.palyrobotics.frc2016.actions.BasicActions.IntakeBall;
import com.palyrobotics.frc2016.actions.BasicActions.ShooterDown;
import com.palyrobotics.frc2016.actions.BasicActions.ShooterUp;
import com.palyrobotics.frc2016.actions.ShootSequence;
import com.palyrobotics.frc2016.auto.AutoMode;
import com.palyrobotics.frc2016.routines.AutoAlignmentRoutine;
import com.palyrobotics.frc2016.routines.EncoderDriveRoutine;

/**
 * Goes under the low bar then shoots a high goal
 */
public class TwentyPointAuto extends AutoMode {
	
	@Override
	protected void routine() {
		
		execute(
				parallel(
						new EncoderDriveRoutine(Constants.kLowBarDistance),
						timed(new ShooterDown(), 1)
						)
				);
		/* Auto Align then high goal */
		execute(new AutoAlignmentRoutine());
		execute(
				parallel(
						timed(new ShooterUp(), 1),
						timed(new IntakeBall(), 1)
					)
				);
		execute(new ShootSequence());
		wait(0.5);
		/* Bring shooter down if extra time */
		execute(timed(new ShooterDown(), 0.25));
		
	}
	
	@Override
	public String toString() {
		return "TwentyPointAuto";
	}
}
