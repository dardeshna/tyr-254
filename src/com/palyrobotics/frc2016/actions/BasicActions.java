package com.palyrobotics.frc2016.actions;

import static com.palyrobotics.frc2016.HardwareAdaptor.kIntake;
import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.frc2016.subsystems.Intake.IntakeState;
import com.palyrobotics.frc2016.subsystems.Shooter.ShooterState;
import com.palyrobotics.lib.util.routines.Action;

public class BasicActions {

	public static class ShooterDown extends Action {	
		public ShooterDown() {
			requires(kShooter);
		}
		@Override
		public void update() {
			kShooter.setState(ShooterState.LOWERED);
		}
	}
	
	public static class ShooterUp extends Action {
		public ShooterUp() {
			requires(kShooter);
		}
		@Override
		public void update() {
			kShooter.setState(ShooterState.RAISED);
		}
	}
	
	public static class IntakeBall extends Action {
		public IntakeBall() {
			requires(kIntake);
		}
		@Override
		public void update() {
			kIntake.setState(IntakeState.INTAKING);
		}
	}
	
	public static class ExpelBall extends Action {
		public ExpelBall() {
			requires(kIntake);
		}
		@Override
		public void update() {
			kIntake.setState(IntakeState.EXPELLING);
		}
	}
	
}
