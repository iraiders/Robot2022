// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class AlignToGoal extends CommandBase {

  SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(
    Constants.AutoConstants.ksVolts,
    Constants.AutoConstants.kvVoltSecondsPerMeter,
    Constants.AutoConstants.kaVoltSecondsSquaredPerMeter);

    PIDController leftController = new PIDController(Constants.AutoConstants.kPDriveVel,0, 0);
    PIDController rightController = new PIDController(Constants.AutoConstants.kPDriveVel,0, 0);
    PIDController rotatController = new PIDController(Constants.LimelightConstants.rotationKP.get(), 0, 0);

    DriveSubsystem driveSubsystem;
    LimelightSubsystem limelightSubsystem;
    
    
  /** Creates a new AlignToGoal. */
  public AlignToGoal(DriveSubsystem driveSubsystem, LimelightSubsystem limelightSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveSubsystem = driveSubsystem;
    this.limelightSubsystem = limelightSubsystem;

    addRequirements(driveSubsystem, limelightSubsystem);

    rotatController.setTolerance(Constants.LimelightConstants.rotationalTolerance.get());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(Constants.tuningMode)
    {
      rotatController = new PIDController(Constants.LimelightConstants.rotationKP.get(), 0, 0);
      rotatController.setTolerance(Constants.LimelightConstants.rotationalTolerance.get());
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = limelightSubsystem.getHorizontalOffset();
    double targetWheelSpeed = rotatController.calculate(error, 0);
    double leftOutput = leftController.calculate(driveSubsystem.getWheelSpeeds().leftMetersPerSecond, targetWheelSpeed + feedForward.calculate(targetWheelSpeed));
    double rightOutput = rightController.calculate(driveSubsystem.getWheelSpeeds().rightMetersPerSecond, targetWheelSpeed + feedForward.calculate(targetWheelSpeed));

      driveSubsystem.tankDriveVolts(leftOutput, -rightOutput);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return rotatController.atSetpoint();
  }
}