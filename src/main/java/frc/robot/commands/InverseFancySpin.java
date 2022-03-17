package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class InverseFancySpin extends CommandBase {
  private final DriveSubsystem drive;
  private double startingAngle;
  private int count = 0;
  private double turnSpeed = 0;

  public InverseFancySpin(DriveSubsystem drive) {
    this.drive = drive;
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    startingAngle = drive.getDegrees();
  }

  @Override
  public void execute() {
    drive.GTADrive(0, 0, turnSpeed);
    count++;
    turnSpeed = (0.4 / 324) * Math.pow((count - 18), 2);
  }

  @Override
  public void end(boolean interrupted) {
    drive.GTADrive(0, 0, 0);
    count = 0;
  }

  @Override
  public boolean isFinished() {
    return count > 36;
    // return !((drive.getDegrees() - startingAngle) <= 360);
  }
}
