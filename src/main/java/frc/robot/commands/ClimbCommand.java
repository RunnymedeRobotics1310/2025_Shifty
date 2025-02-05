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

    }

    @Override
    public void execute() {

        // FIXME: this command is a 'one-shot' command. It will call
        // out to the subsystem, and then end immediately.
        // In this case, you could put all of the code in the initialize
        // method and have an empty execute method. It is clearer that
        // the command does nothing each loop, and all of the work
        // is done in the initialize.

        // FIXME: do the state check in the subsystem (if you need to).
        // remove the climbSystem.isClimbDeployed(), and put that check
        // in the subsystem.extendClimbPiston instead.
        if (deployClimb && !climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(true);
        }
        if (!deployClimb && climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(false);
        }
    }

    @Override
    public boolean isFinished() {

        // FIXME: this command can be a 'one shot' command.
        // it should just end immediately.

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
