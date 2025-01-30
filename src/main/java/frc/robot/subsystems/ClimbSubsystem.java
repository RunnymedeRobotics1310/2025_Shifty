package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

    private final LightsSubsystem lightsSubsystem;

    // FIXME: Move the shifter from the drive to here to rough in the climb
    private boolean               climbDeployed = false;

    // Simulation constants

    public ClimbSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    /*
     * Climb Routines
     * FIXME: Make some arm routines
     */
    public boolean isClimbDeployed() {
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
