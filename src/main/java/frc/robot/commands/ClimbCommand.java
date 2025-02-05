package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends LoggingCommand {

    private final boolean        deployClimb;
    private final ClimbSubsystem climbSubsystem;

    public ClimbCommand(boolean deployClimb, ClimbSubsystem climbSubsystem) {

        this.deployClimb    = deployClimb;
        this.climbSubsystem = climbSubsystem;

        addRequirements(climbSubsystem);

    }

    @Override
    public void initialize() {

        logCommandStart();

        // if (deployClimb && !climbSubsystem.isClimbDeployed()) {
        // climbSubsystem.extendClimbPiston(true);
        // }
        // if (!deployClimb && climbSubsystem.isClimbDeployed()) {
        // climbSubsystem.extendClimbPiston(false);
        // }

        if (deployClimb) {
            climbSubsystem.toggleClimbPiston(true);
        }
        else {
            climbSubsystem.toggleClimbPiston(false);
        }

    }

    @Override
    public void execute() {

        // FIXME: do the state check in the subsystem (if you need to).
        // remove the climbSystem.isClimbDeployed(), and put that check
        // in the subsystem.extendClimbPiston instead.

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }

}
