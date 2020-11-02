/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode; 
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.IMotorController;

public class DriveTrain extends Subsystem {

  boolean invert;

  
  //leftMaster.configAllSettings(talonConfig)

  /*rightMaster.configAllSettings(talonFXConfig);
  rightMaster.enableVoltageCompensation(true);
  rightSlave.configFactoryDefault();
  leftMaster.configAllSettings(talonConfig);
  leftMaster.enableVoltageCompensation(true);
  leftSlave.configFactoryDefault();
  */
  public DriveTrain() {

  }

  @Override
  public void periodic() {
    setDefaultCommand(new TankDrive());
  }

@Override
protected void initDefaultCommand() {
	// TODO Auto-generated method stub
	
}
}
