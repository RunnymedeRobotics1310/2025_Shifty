package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlgaeConstants;

public class AlgaeSubsystem extends SubsystemBase {

    private final LightsSubsystem lightsSubsystem;

    // Algae Subsystem Motors
    private final TalonSRX        intakeMotor = new TalonSRX(AlgaeConstants.INTAKE_MOTOR_CAN_ID);

    private double                intakeSpeed = 0;

    private boolean               armDeployed = false;

    // Simulation constants

    public AlgaeSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    /*
     * Intake Routines
     */

    public void setIntakeSpeed(double speed) {

        this.intakeSpeed = speed;

        checkSafety();

        // intakeMotor.set(ControlMode.PercentOutput, intakeSpeed);
    }

    /*
     * Arm Routines
     * FIXME: Make some arm routines
     */
    public boolean isArmDeployed() {
        return armDeployed;
    }


    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        checkSafety();

        // FIXME: Add a call to the lights subsystem to show the current speed or height
        // lightsSubsystem.setAlgae..();

        SmartDashboard.putNumber("Algae Intake Motor", intakeSpeed);
        SmartDashboard.putBoolean("Algae Arm Deployed", isArmDeployed());

        // FIXME: what else should we put on the SmartDashboard
    }

    private void checkSafety() {

        // Are there any safety checks?
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
            .append("intake: speed ").append(intakeSpeed);

        return sb.toString();
    }
}
