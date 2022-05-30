package frc.robot.subsystems.DriveIO;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveIO.DriveIO.DriveInputs;

public class BabyDriver extends SubsystemBase {

  DriveIO io;
  DriveInputs inputs = new DriveInputs();

  public BabyDriver(DriveIO driveIO) {
    io = driveIO;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);

    SmartDashboard.putNumber("Left Enc", inputs.leftEncPosition);
    SmartDashboard.putNumber("Right Enc", inputs.rightEncPosition);

    SmartDashboard.putNumber("Odo X", inputs.odoX);
    SmartDashboard.putNumber("Odo Y", inputs.odoY);
    SmartDashboard.putNumber("Odo H", inputs.odoH);

    SmartDashboard.putNumber("L1 Current", inputs.frontLeftCurrent);
    SmartDashboard.putNumber("R1 Current", inputs.frontRightCurrent);
    //   SmartDashboard.putNumber("L2 Current", right1.getOutputCurrent());
    //   SmartDashboard.putNumber("R2 Current", right2.getOutputCurrent());
  }

  public void setPercent(double leftPercent, double rightPercent) {
    io.setVoltage(
        leftPercent * Constants.AutoConstants.maxVoltageApplied,
        rightPercent * Constants.AutoConstants.maxVoltageApplied);
  }

  public double getAverageEncoderDist() {
    return ((inputs.leftEncPosition + inputs.rightEncPosition) / 2.0);
  }

  public void GTADrive(double leftTrigger, double rightTrigger, double turn) {
    turn = MathUtil.applyDeadband(turn, Constants.DriveConstants.kJoystickTurnDeadzone);
    turn = turn * turn * Math.signum(turn);

    double left = rightTrigger - leftTrigger + turn;
    double right = rightTrigger - leftTrigger - turn;
    left = Math.min(1.0, Math.max(-1.0, left));
    right = Math.max(-1.0, Math.min(1.0, right));

    setPercent(left, right);
  }

  public void CarDrive(double leftTrigger, double rightTrigger, double turn) {
    turn = MathUtil.applyDeadband(turn, Constants.DriveConstants.kJoystickTurnDeadzone);
    turn = turn * turn * Math.signum(turn);
    if (rightTrigger < leftTrigger) {

      double left = rightTrigger - leftTrigger - turn;
      double right = rightTrigger - leftTrigger + turn;
      left = Math.min(1.0, Math.max(-1.0, left));
      right = Math.max(-1.0, Math.min(1.0, right));

      setPercent(left, right);
    } else {
      double left = rightTrigger - leftTrigger + turn;
      double right = rightTrigger - leftTrigger - turn;
      left = Math.min(1.0, Math.max(-1.0, left));
      right = Math.max(-1.0, Math.min(1.0, right));

      setPercent(left, right);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
