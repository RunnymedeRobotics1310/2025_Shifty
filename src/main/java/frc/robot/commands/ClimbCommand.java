package frc.robot.commands;

import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends LoggingCommand {

    private final boolean        deployClimb;
    private final ClimbSubsystem climbSubsystem;
    private final double         duraitionMillis;
    private long                 startTimeMillis;

    public ClimbCommand(double duraitionMillis, boolean deployClimb, ClimbSubsystem climbSubsystem) {

        this.deployClimb     = deployClimb;
        this.climbSubsystem  = climbSubsystem;
        this.duraitionMillis = duraitionMillis;

        addRequirements(climbSubsystem);

    }

    @Override
    public void initialize() {

        logCommandStart();
        // FIXME: you do not need to capture the start time,
        // the logging command does that automatically
        startTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void execute() {

        // FIXME: what if false is passed in - are you using the same command
        // for extending and retracting the climb?
        // If so, maybe this would be the SetClimbCommand(newState, climbSubsystem)

        if (deployClimb && !climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(true);
        }
    }

    @Override
    public boolean isFinished() {

        // FIXME: why wait some number of milliseconds before ending the command?
        // Is that necessary?

        // To check an elapsed duration use the method
        // if (hasElapsed(durationInSeconds)) { ...
        // The start time is captured in the logCommandStart, and the
        // hasElapsed method returns true if the number of seconds has elapsed since the
        // start of the command.
        return duraitionMillis < System.currentTimeMillis() - startTimeMillis;
    }

    // FIXME: always add an end routine here and logCommandEnd

}
