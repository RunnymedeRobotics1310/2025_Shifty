package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlgaeConstants;
import frc.robot.Constants.DriveConstants;

public class AlgaeSubsystem extends SubsystemBase {

    private final LightsSubsystem lightsSubsystem;

    // Algae Subsystem Motors
    private final TalonSRX        intakeMotor    = new TalonSRX(AlgaeConstants.INTAKE_MOTOR_CAN_ID);

    private double                intakeSpeed    = 0;

    private Solenoid              algaeArmPiston = new Solenoid(PneumaticsModuleType.CTREPCM,
        DriveConstants.SHIFTER_PNEUMATIC_PORT);


    public AlgaeSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    /*
     * Intake Routines
     */


    public void setIntakeSpeed(double speed) {

        this.intakeSpeed = speed;

        checkSafety();

        intakeMotor.set(ControlMode.PercentOutput, intakeSpeed);

    }

    /*
     * Arm Routines
     */

    public void deployArm(boolean shift) {
        algaeArmPiston.set(shift);
    }

    public boolean isArmDeployed() {
        return algaeArmPiston.get();
    }

    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        checkSafety();

        lightsSubsystem.setAlgaeIntakeLights(intakeSpeed, isArmDeployed());

        SmartDashboard.putNumber("Algae Intake Motor", intakeSpeed);
        SmartDashboard.putBoolean("Algae Arm Deployed", isArmDeployed());

    }

    private void checkSafety() {

        // Are there any safety checks?
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
            .append("intake: speed ").append(intakeSpeed).append("arm deployed: ").append(isArmDeployed());

        return sb.toString();
    }
}
