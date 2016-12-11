package com.palyrobotics.frc2016.routines;

import static com.palyrobotics.frc2016.HardwareAdaptor.kIntake;
import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.frc2016.Requirables;
import com.palyrobotics.lib.util.routines.Routine;

/**
 * Contains basic robot routines that are one-time actions
 * @author Devin
 *
 */
public class BasicRoutines {

	public static class ShooterDown extends Routine {	
		public ShooterDown() {
			requires(Requirables.rShooterMotor);
		}
		@Override
		public void start() {
			kShooter.lower();
		}
		public void cleanup() {
			kShooter.idle();
		}
		@Override
		public String getName() {
			return "ShooterDownAction";
		}
	}
	
	public static class ShooterUp extends Routine {
		public ShooterUp() {
			requires(Requirables.rShooterMotor);
		}
		@Override
		public void start() {
			kShooter.raise();
		}
		public void cleanup() {
			kShooter.idle();
		}
		@Override
		public String getName() {
			return "ShooterUpAction";
		}
	}
	
	public static class IntakeBall extends Routine {
		public IntakeBall() {
			requires(Requirables.rIntake);
		}
		@Override
		public void start() {
			kIntake.intake();
		}
		public void cleanup() {
			kIntake.idle();
		}
		@Override
		public String getName() {
			return "IntakeBallAction";
		}
	}
	
	public static class ExpelBall extends Routine {
		public ExpelBall() {
			requires(Requirables.rIntake);
		}
		@Override
		public void start() {
			kIntake.expel();
		}
		public void cleanup() {
			kIntake.idle();
		}
		@Override
		public String getName() {
			return "ExpelBallAction";
		}
	}
	
}
