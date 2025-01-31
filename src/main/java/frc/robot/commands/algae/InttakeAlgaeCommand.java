package frc.robot.commands.algae;

import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.AlgaeSubsystem;

public class InttakeAlgaeCommand extends LoggingCommand {

    private final OperatorInput  operatorInput;
    private final AlgaeSubsystem algaeSubsystem;

    public InttakeAlgaeCommand(OperatorInput operatorInput, AlgaeSubsystem algaeSubsystem) {

        this.operatorInput  = operatorInput;
        this.algaeSubsystem = algaeSubsystem;

        addRequirements(algaeSubsystem);

    }

    public void initialize() {
        // placeholder
        logCommandStart();
    }

    public void execute() {
        double intakeSpeed = operatorInput.inTakeAlgae();
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

