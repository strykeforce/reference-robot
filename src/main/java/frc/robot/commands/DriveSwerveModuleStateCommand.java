package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import org.strykeforce.swerve.SwerveModule;

public class DriveSwerveModuleStateCommand extends CommandBase {

  private final SwerveModuleState desiredState;
  private final DriveSubsystem driveSubsystem;

  public DriveSwerveModuleStateCommand(
      SwerveModuleState desiredState, DriveSubsystem driveSubsystem) {
    this.desiredState = desiredState;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    setSwerveModuleState(desiredState);
  }

  @Override
  public void end(boolean interrupted) {
    setSwerveModuleState(new SwerveModuleState(0.0, new Rotation2d()));
  }

  private void setSwerveModuleState(SwerveModuleState state) {
    SwerveModule[] modules = driveSubsystem.getSwerveModules();
    for (SwerveModule module : modules) {
      module.setDesiredState(state);
    }
  }
}
