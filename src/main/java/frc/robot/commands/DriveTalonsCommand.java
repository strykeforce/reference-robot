package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import org.strykeforce.swerve.SwerveModule;
import org.strykeforce.swerve.TalonSwerveModule;

public class DriveTalonsCommand extends CommandBase {

  private final double driveTalonPercentOutput;
  private final double azimuthTalonCounts;
  private final DriveSubsystem driveSubsystem;

  public DriveTalonsCommand(double driveTalonPercentOutput, double azimuthTalonCounts,
      DriveSubsystem driveSubsystem) {
    this.driveTalonPercentOutput = driveTalonPercentOutput;
    this.azimuthTalonCounts = azimuthTalonCounts;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    setTalons(driveTalonPercentOutput, azimuthTalonCounts);
  }

  @Override
  public void end(boolean interrupted) {
    var module = (TalonSwerveModule) driveSubsystem.getSwerveModules()[0];
    setTalons(0, module.getAzimuthTalon().getSelectedSensorPosition());
  }

  private void setTalons(double drive, double azimuth) {
    var modules = driveSubsystem.getSwerveModules();
    for (SwerveModule swerveModule : modules) {
      var module = (TalonSwerveModule) swerveModule;
      module.getDriveTalon().set(ControlMode.PercentOutput, drive);
      module.getAzimuthTalon().set(ControlMode.MotionMagic, azimuth);
    }
  }
}
