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

    private boolean               climbDeployed = false;

    public ClimbSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    /**
     * FIXME: is this a good name for this routine?
     * TODO: add JavaDoc to activation routines.
     *
     * @param deployClimb {@code true} to deploy the climber (climb), {@code false} to retract the
     * climber (lower the robot)
     */
    public void extendClimbPiston(boolean deployClimb) {
        climbPiston.set(deployClimb);
    }

    public boolean isClimbDeployed() {
        // FIXME: the climbDeployed variable is never used, do we need it?
        climbDeployed = climbPiston.get();
        return climbDeployed;
    }


    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        checkSafety();

        lightsSubsystem.setClimbDeployedLights(isClimbDeployed());

        SmartDashboard.putBoolean("Climb Deployed", isClimbDeployed());
        SmartDashboard.putBoolean("Can deploy climb", true);

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
