package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.SnekSystem;

public class ShootALowBall extends SequentialCommandGroup {

  public ShootALowBall(ShootSubsystem shootSubsystem, SnekSystem snekSystem) {
    addCommands(
        new InstantCommand(
            () -> {
              shootSubsystem.setTargetRPM(Constants.ShooterConstants.RPM);
            }),
        new WaitCommand(0.5),
        new InstantCommand(
            () -> {
              snekSystem.setUpperSnekSpeed(0.5);
            }),
        new WaitCommand(0.5),
        new InstantCommand(
            () -> {
              shootSubsystem.setTargetRPM(Constants.zero);
            }));
  }
}
