/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private Joystick leftJoystick;
  private Joystick rightJoystick;


  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {

    //Grouping left motors
    Spark frontLeft = new Spark(1);
    Spark rearLeft = new Spark(2);
    
    leftMotors = new SpeedControllerGroup(frontLeft, rearLeft);
    
    //Grouping right motors

    Spark frontRight = new Spark(3);
    Spark rearRight = new Spark(4);

    rightMotors = new SpeedControllerGroup(frontRight, rearRight);

    //Tank Drive
    TankConstructor = new DifferentialDrive(leftMotors, rightMotors);

    // Joystick
    leftJoystick = new Joystick(0);
    rightJoystick = new Joystick(1);
  
  }

  @Override
  public void robotPeriodic() {
   
  }

  @Override
  public void autonomousInit() {
    //Enable motors
    m_autonomousCommand = m_chooser.getSelected();

    //Schedules autonomous commands
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }


  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }
  

  @Override
  public void teleopInit() {
    //Stops autonomous commands
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
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


}
