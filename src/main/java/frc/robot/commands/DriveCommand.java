package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {

  private final double vx;
  private final double vy;
  private final double omega;
  private final DriveSubsystem driveSubsystem;

  public DriveCommand(double vx, double vy, double omega,
      DriveSubsystem driveSubsystem) {
    this.vx = vx;
    this.vy = vy;
    this.omega = omega;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    driveSubsystem.drive(vx, vy, omega);
  }

  @Override
  public void end(boolean interrupted) {
    driveSubsystem.drive(0.0, 0.0, 0.0);
  }
}
