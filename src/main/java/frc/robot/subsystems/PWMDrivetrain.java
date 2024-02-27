// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* This class declares the subsystem for the robot drivetrain if controllers are connected via PWM. If using SPARK MAX
 * controllers connected to CAN, go to RobotContainer and comment out the line declaring this subsystem and uncomment
 * the line for the CANDrivetrain.
 *
 * The subsystem contains the objects for the hardware contained in the mechanism and handles low level logic
 * for control. Subsystems are a mechanism that, when used in conjuction with command "Requirements", ensure
 * that hardware is only being used by 1 command at a time.
 */
public class PWMDrivetrain extends SubsystemBase {
  /*Class member variables. These variables represent things the class needs to keep track of and use between
  different method calls. */
  DifferentialDrive m_drivetrain;
  PWMSparkMax m_leftFront, m_leftRear, m_rightFront, m_rightRear;

  /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
   * member variables and perform any configuration or set up necessary on hardware.
   */
  public PWMDrivetrain() {
    /*Create MotorControllerGroups for each side of the drivetrain. These are declared here, and not at the class level
     * as we will not need to reference them directly anymore after we put them into a DifferentialDrive.
     */
    m_leftFront = new PWMSparkMax(kLeftFrontID);
    m_leftRear = new PWMSparkMax(kLeftRearID);
    m_rightFront = new PWMSparkMax(kRightFrontID);
    m_rightRear = new PWMSparkMax(kRightRearID);

    // Invert the left side of the robot motors so they spin
    // the same direction when the same input is requested
    m_leftFront.setInverted(true);
    m_leftRear.setInverted(true);


    /* This creates the DifferentialDrive object that will be used to drive the motors.
     * Normally in drivetrains the motor controllers on the same side of the robot 
     * follow each other, but we can't do that with PWMSparkMax.  This will construct the
     * object as if they were followers.
     */
    m_drivetrain = new DifferentialDrive(
      (double output) -> {
        m_leftFront.set(output);
        m_leftRear.set(output);
      },
      (double output) -> {
        m_rightFront.set(output);
        m_rightRear.set(output);
      }
    );

    
  }

  /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
   * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
  public void arcadeDrive(double speed, double rotation) {
    m_drivetrain.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
    /*This method will be called once per scheduler run. It can be used for running tasks we know we want to update each
     * loop such as processing sensor data. Our drivetrain is simple so we don't have anything to put here */
  }
}
