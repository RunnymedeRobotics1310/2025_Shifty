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
    public void execute() {
        if (deployClimb && !climbSubsystem.isClimbDeployed()) {
            climbSubsystem.extendClimbPiston(true);
        }
    }

    @Override
    public void initialize() {
        logCommandStart();
        startTimeMillis = System.currentTimeMillis();
    }

    @Override
    public boolean isFinished() {
        return duraitionMillis < System.currentTimeMillis() - startTimeMillis;
    }


}
