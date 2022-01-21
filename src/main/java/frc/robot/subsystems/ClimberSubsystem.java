package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase {

    private final CANSparkMax telescope;

    public ClimberSubsystem (){
        telescope = new CANSparkMax(RobotMap.telescope, MotorType.kBrushless);
        telescope.restoreFactoryDefaults(); //Seems like an important thing
        telescope.setIdleMode(IdleMode.kBrake/*Might change idk what we want*/);
        telescope.setInverted(true/*I think that's how gear stuff shall require it*/);
        telescope.getPIDController().setP(ClimberConstants.kP);
        telescope.setSmartCurrentLimit(ClimberConstants.kCurrentLimit);
    }

    public void setTelescopeSpeed(double speed){
        telescope.set(speed);
    }

    public void resetTelescopeEncoder(){
        telescope.getEncoder().setPosition(0);
    }

    public double getHeight(){
        return telescope.getEncoder().getPosition();
    }

    public void setHeight(double height){
        telescope.getPIDController().setReference(height, CANSparkMax.ControlType.kPosition);
    }

}
