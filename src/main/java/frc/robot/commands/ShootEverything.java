// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.SnekSystem;

public class ShootEverything extends SequentialCommandGroup {
  /** Creates a new ShootEverything. */
  SnekSystem snekSystem;

  public ShootEverything(SnekSystem snekSystem, ShootSubsystem shootSubsystem) {
    this.snekSystem = snekSystem;
    addCommands(
        // new RunCommand(
        // () -> {
        // snekSystem.setUpperSnekSpeed(-0.4);
        // snekSystem.setLowerSnekSpeed(-0.1);
        // },
        // snekSystem)
        // .withTimeout(0.25),
        new PrepShotLow(shootSubsystem, snekSystem, false),
        new FinishShot(snekSystem, shootSubsystem));
  }
}
