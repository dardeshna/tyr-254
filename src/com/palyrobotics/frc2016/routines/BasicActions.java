package com.palyrobotics.frc2016.routines;

import static com.palyrobotics.frc2016.HardwareAdaptor.kIntake;
import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.lib.util.routines.Routine;

/**
 * Contains basic robot routines that are one-time actions
 * @author Devin
 *
 */
public class BasicActions {

	public static class ShooterDown extends Routine {	
		public ShooterDown() {
			requires(kShooter);
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
			requires(kShooter);
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
			requires(kIntake);
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
			requires(kIntake);
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
