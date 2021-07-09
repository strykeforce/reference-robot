package frc.robot;

import static frc.robot.Constants.kJoystickDeadband;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveSwerveModuleStateCommand;
import frc.robot.commands.DriveTalonsCommand;
import frc.robot.subsystems.DriveSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strykeforce.telemetry.TelemetryController;
import org.strykeforce.telemetry.TelemetryService;

public class RobotContainer {

  private static final Logger logger = LoggerFactory.getLogger(RobotContainer.class);

  private final TelemetryService telemetryService = new TelemetryService(TelemetryController::new);
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(telemetryService);
  private final Joystick joystick = new Joystick(0);

  public RobotContainer() {
    configureButtonBindings();

    driveSubsystem.setDefaultCommand(new RunCommand(
        () -> {
          double vx = getLeftX() * -DriveConstants.kMaxSpeedMetersPerSecond;
          double vy = getLeftY() * -DriveConstants.kMaxSpeedMetersPerSecond;
          double omega = getRightY() * -DriveConstants.kMaxOmega;
          driveSubsystem.drive(vx, vy, omega);
        }
        , driveSubsystem));

    telemetryService.register(driveSubsystem);
    telemetryService.start();
  }

  private void configureButtonBindings() {
    new JoystickButton(joystick, InterlinkButton.X.id)
        .whenHeld(new DriveCommand(0.5, 0, 0, driveSubsystem));

    new JoystickButton(joystick, InterlinkButton.HAMBURGER.id)
        .whenHeld(new DriveSwerveModuleStateCommand(new SwerveModuleState(0.5, new Rotation2d()),
            driveSubsystem));

    new JoystickButton(joystick, InterlinkButton.DOWN.id)
        .whenHeld(new DriveTalonsCommand(0.1, 0, driveSubsystem));

    new JoystickButton(joystick, InterlinkButton.UP.id)
        .whenHeld(new DriveCommand(0, 0, 0.4, driveSubsystem));

  }

  ///////////////////////////////////////////////////////////////////
  // InterLink Controls
  ///////////////////////////////////////////////////////////////////

  /**
   * Left stick X (up-down) axis.
   */
  public double getLeftX() {
    double val = joystick.getRawAxis(Axis.LEFT_X.id);
    if (Math.abs(val) < kJoystickDeadband) {
      return 0.0;
    }
    return val;
  }

  /**
   * Left stick Y (left-right) axis.
   */
  public double getLeftY() {
    double val = joystick.getRawAxis(Axis.LEFT_Y.id);
    if (Math.abs(val) < kJoystickDeadband) {
      return 0.0;
    }
    return val;
  }

  /**
   * Right stick Y (left-right) axis.
   */
  public double getRightY() {
    double val = joystick.getRawAxis(Axis.RIGHT_Y.id);
    if (Math.abs(val) < kJoystickDeadband) {
      return 0.0;
    }
    return val;
  }

  public enum Axis {
    RIGHT_X(1),
    RIGHT_Y(0),
    LEFT_X(2),
    LEFT_Y(5),
    TUNER(6),
    LEFT_BACK(4),
    RIGHT_BACK(3);

    private final int id;

    Axis(int id) {
      this.id = id;
    }
  }

  public enum Shoulder {
    RIGHT_DOWN(2),
    LEFT_DOWN(4),
    LEFT_UP(5);

    private final int id;

    Shoulder(int id) {
      this.id = id;
    }
  }

  public enum Toggle {
    LEFT_TOGGLE(1);

    private final int id;

    Toggle(int id) {
      this.id = id;
    }
  }

  public enum InterlinkButton {
    RESET(3),
    HAMBURGER(14),
    X(15),
    UP(16),
    DOWN(17);

    private final int id;

    InterlinkButton(int id) {
      this.id = id;
    }
  }

  public enum Trim {
    LEFT_Y_POS(7),
    LEFT_Y_NEG(6),
    LEFT_X_POS(8),
    LEFT_X_NEG(9),
    RIGHT_X_POS(10),
    RIGHT_X_NEG(11),
    RIGHT_Y_POS(12),
    RIGHT_Y_NEG(13);

    private final int id;

    Trim(int id) {
      this.id = id;
    }
  }
}
