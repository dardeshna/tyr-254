package com.palyrobotics.frc2016.auto.modes;

import com.palyrobotics.frc2016.Constants;
import com.palyrobotics.frc2016.auto.AutoMode;
import com.palyrobotics.frc2016.routines.AutoAlignmentRoutine;
import com.palyrobotics.frc2016.routines.BasicRoutines.IntakeBall;
import com.palyrobotics.frc2016.routines.BasicRoutines.ShooterDown;
import com.palyrobotics.frc2016.routines.BasicRoutines.ShooterUp;
import com.palyrobotics.frc2016.routines.EncoderDriveRoutine;
import com.palyrobotics.frc2016.routines.ShootRoutine;

/**
 * Goes under the low bar then shoots a high goal
 */
public class TwentyPointAuto extends AutoMode {
	
	@Override
	protected void routine() {
		
		/* Drive forward */
		execute(
				parallel(
						new EncoderDriveRoutine(Constants.kLowBarDistance),
						timed(new ShooterDown(), .5)
						)
				);
		
		/* Auto Align then high goal */
		execute(new AutoAlignmentRoutine());
		execute(
				parallel(
						doUntil(
								parallel(
										new ShooterUp(),
										new IntakeBall()
									),
								sequential(	
										waitFor(1),
										new ShootRoutine()
									)
							)
					)
			);

		/* Bring shooter down if extra time */
		execute(timed(new ShooterDown(), 0.25));
		
	}
	
	@Override
	public String toString() {
		return "TwentyPointAuto";
	}
}
