package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends LoggingCommand {

    private final boolean        deployClimb;
    private final ClimbSubsystem climbSubsystem;

    public ClimbCommand(double duraitionMillis, boolean deployClimb, ClimbSubsystem climbSubsystem) {

        this.deployClimb    = deployClimb;
        this.climbSubsystem = climbSubsystem;

        addRequirements(climbSubsystem);

    }

    @Override
    public void initialize() {

        logCommandStart();

    }

    @Override
    public void execute() {

        if (deployClimb && !climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(true);
        }
        if (!deployClimb && climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(false);
        }
    }

    @Override
    public boolean isFinished() {

        if (climbSubsystem.isClimbDeployed() && deployClimb) {
            return true;
        }
        else {
            if (!climbSubsystem.isClimbDeployed() && !deployClimb) {
                return true;
            }
            else {
                return false;
            }
        }

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }

}
