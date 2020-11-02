/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  private DifferentialDrive TankConstructor;

   //Motors
  private final WPI_TalonFX leftMaster = new WPI_TalonFX(0);
  private final WPI_TalonFX leftSlave = new WPI_TalonFX(0);
  private final WPI_TalonFX rightMaster = new WPI_TalonFX(0);
  private final WPI_TalonFX rightSlave = new WPI_TalonFX(0);

  private DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  //Joystick
  private Joystick leftJoystick = new Joystick(0);
  private Joystick rightJoystick = new Joystick(1);

  //Encoder Conversion
  private final double kDriveTick2Feet = 1.0 / 2048 * 6 * Math.PI / 12;
  
  @Override
  public void robotInit() {
    // Inverting Motors
    leftMaster.setInverted(true);
    rightMaster.setInverted(true);

    // Setting up the slaves
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftSlave.setInverted(InvertType.FollowMaster);
    rightSlave.setInverted(InvertType.FollowMaster);

    // Init of encoders
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    leftMaster.setSensorPhase(false);
    rightMaster.setSensorPhase(true);

    // Seting Encoders to 0
    leftMaster.setSelectedSensorPosition(0, 0, 10);
    rightMaster.setSelectedSensorPosition(0, 0, 10);

    drive.setDeadband(0.05);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Left Drive Encoder Value", leftMaster.getSelectedSensorPosition() * kDriveTick2Feet);
    SmartDashboard.putNumber("Right Drive Encoder Value", rightMaster.getSelectedSensorPosition() * kDriveTick2Feet);
  }

  @Override
  public void autonomousInit() {
    enableMotors(true);
    //Setting Encoders to 0
    leftMaster.setSelectedSensorPosition(0, 0, 10);
    rightMaster.setSelectedSensorPosition(0, 0, 10);
  }


  @Override
  public void autonomousPeriodic() {
    double leftPosition = leftMaster.getSelectedSensorPosition() * kDriveTick2Feet;
    double rightPosition = rightMaster.getSelectedSensorPosition() * kDriveTick2Feet;
    double distance = (leftPosition + rightPosition) / 2;

    if (distance < 10) {
      drive.tankDrive(0.6, 0.6);
    } else {
      drive.tankDrive(0, 0);
    }
  }
  

  @Override
  public void teleopInit() {
    enableMotors(true);
  }

  @Override
  public void teleopPeriodic() {
    //Driving
    TankConstructor.tankDrive(leftJoystick.getY()/-1, rightJoystick.getY()/-1);
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  //Enabling motors
  private void enableMotors(final boolean on) {
    NeutralMode mode;
    if (on) {
      mode = NeutralMode.Brake;
    } else {
      mode = NeutralMode.Coast;
    }
    leftMaster.setNeutralMode(mode);
    rightMaster.setNeutralMode(mode);
    leftSlave.setNeutralMode(mode);
    rightSlave.setNeutralMode(mode);
  }
}
