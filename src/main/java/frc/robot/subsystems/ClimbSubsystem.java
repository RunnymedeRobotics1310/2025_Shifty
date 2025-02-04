package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class ClimbSubsystem extends SubsystemBase {

    private Solenoid              climbPiston   = new Solenoid(PneumaticsModuleType.CTREPCM,
        DriveConstants.SHIFTER_PNEUMATIC_PORT);


    private final LightsSubsystem lightsSubsystem;

    // FIXME: Move the shifter from the drive to here to rough in the climb
    private boolean               climbDeployed = false;

    // Simulation constants

    public ClimbSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    public void extendClimbPiston(boolean deployClimb) {
        // FIXME: Capture the passed in state
        // The variable climbDeployed needs to track the last value
        // passed into the extendClimbPison method.
        climbPiston.set(deployClimb);
    }

    /*
     * Climb Routines
     */
    public boolean isClimbDeployed() {
        climbDeployed = climbPiston.get();
        return climbDeployed;
    }


    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        checkSafety();

        // FIXME: Add a call to the lights subsystem to show the current speed or height
        // lightsSubsystem.setClimb..();

        lightsSubsystem.setClimbLights(isClimbDeployed());

        SmartDashboard.putBoolean("Climb Deployed", isClimbDeployed());


        // FIXME: what else should we put on the SmartDashboard
    }

    private void checkSafety() {

        // Are there any safety checks?
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
            .append("climb: ").append(isClimbDeployed());

        return sb.toString();
    }
}
