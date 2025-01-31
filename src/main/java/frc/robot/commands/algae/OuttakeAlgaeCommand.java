package frc.robot.commands.algae;

import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.AlgaeSubsystem;

public class OuttakeAlgaeCommand extends LoggingCommand {

    private final OperatorInput  operatorInput;
    private final AlgaeSubsystem algaeSubsystem;

    public OuttakeAlgaeCommand(OperatorInput operatorInput, AlgaeSubsystem algaeSubsystem) {

        this.operatorInput  = operatorInput;
        this.algaeSubsystem = algaeSubsystem;

        addRequirements(algaeSubsystem);

    }

    public void initialize() {
        // placeholder
    }

    public void execute() {
        // placeholder
    }

    public boolean isFinished() {
        return false;
        // placeholder
    }

    public void end(boolean interrupted) {

        logCommandEnd(interrupted);

    }

}