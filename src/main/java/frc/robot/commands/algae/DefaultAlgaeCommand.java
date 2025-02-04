package frc.robot.commands.algae;


import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.AlgaeSubsystem;


public class DefaultAlgaeCommand extends LoggingCommand {

    private final AlgaeSubsystem algaeSubsystem;
    private final OperatorInput  operatorInput;
    public boolean               algaeIntakeState  = false;
    public boolean               algaeOuttakeState = false;

    public double                speed             = 0;


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
            algaeIntakeState  = true;
            algaeOuttakeState = false;
        }

        if (operatorInput.startAlgaeIntake() < 0.5) {
            algaeIntakeState = false;
        }

        if (operatorInput.startAlgaeOuttake() >= 0.5) {
            algaeOuttakeState = true;
        }

        if (operatorInput.startAlgaeOuttake() < 0.5) {
            algaeOuttakeState = false;
        }

        if (operatorInput.startAlgaeIntake() >= 0.5 && operatorInput.startAlgaeOuttake() >= 0.5) {
            algaeIntakeState  = false;
            algaeOuttakeState = false;
        }

        if (!algaeIntakeState) {
            speed = 0;
            log("Algae OFF");
        }

        if (algaeIntakeState) {
            speed = 1;
            log("Algae O");
        }


        if (!algaeOuttakeState) {
            log("Outtake OFF");
        }

        else {
            speed = 0.5;
            log("Outtake O");
        }

        System.out.println("DefaultAlgaeCommand speed: " + speed);

        algaeSubsystem.setIntakeSpeed(speed);


    }


    public boolean isFinished() {
        return false;
        // placeholder
    }

    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

    }
}


