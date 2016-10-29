package com.palyrobotics.frc2016.actions;

import static com.palyrobotics.frc2016.HardwareAdaptor.kIntake;
import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.lib.util.routines.Action;

public class BasicActions {

	public static class ShooterDown extends Action {	
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
	}
	
	public static class ShooterUp extends Action {
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
	}
	
	public static class IntakeBall extends Action {
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
	}
	
	public static class ExpelBall extends Action {
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
	}
	
}
