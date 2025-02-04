package frc.robot.commands.algae;


import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.AlgaeSubsystem;


public class DefaultAlgaeCommand extends LoggingCommand {

    private final AlgaeSubsystem algaeSubsystem;
    private final OperatorInput  operatorInput;
    private boolean              algaeIntakeState  = false;
    private boolean              algaeOuttakeState = false;

    private double               intakeSpeed       = 0;


    public DefaultAlgaeCommand(AlgaeSubsystem algaeSubsystem, OperatorInput operatorInput, boolean algaeIntakeState) {

        this.algaeSubsystem = algaeSubsystem;
        this.operatorInput  = operatorInput;

        addRequirements(algaeSubsystem);

    }

    public void initialize() {
        // placeholder
        logCommandStart();
    }

    public void execute() {

        if (operatorInput.startAlgaeIntake() >= 0.5) {
            algaeIntakeState = true;
        }

        if (operatorInput.startAlgaeIntake() <= 0.5) {
            algaeIntakeState = false;
        }

        if (operatorInput.startAlgaeOuttake() >= 0.5) {
            algaeOuttakeState = true;
        }

        if (operatorInput.startAlgaeOuttake() <= 0.5) {
            algaeOuttakeState = false;
        }


        if (algaeIntakeState = false) {
            log("UR SO OFF DUDE");
        }

        if (algaeIntakeState = true) {
            log("SO ONLINE");
        }
    }


    public boolean isFinished() {
        return false;
        // placeholder
    }

    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

    }
}

