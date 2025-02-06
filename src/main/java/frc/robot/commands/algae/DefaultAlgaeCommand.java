package frc.robot.commands.algae;


import frc.robot.Constants.AlgaeConstants;
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

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {

        algaeIntakeState  = false;
        algaeOuttakeState = false;
        speed             = 0;

        if (operatorInput.intakeAlgae()) {
            algaeIntakeState  = true;
            algaeOuttakeState = false;

        }

        if (operatorInput.outtakeAlgae()) {
            algaeIntakeState  = false;
            algaeOuttakeState = true;
        }

        if (operatorInput.intakeAlgae() && operatorInput.outtakeAlgae()) {
            algaeIntakeState  = false;
            algaeOuttakeState = false;
        }

        if (algaeIntakeState) {
            speed = AlgaeConstants.ALGAE_INTAKE_SPEED;
            algaeSubsystem.deployArm(true);
        }
        else {
            if (!algaeOuttakeState) {
                algaeSubsystem.deployArm(false);
                speed = AlgaeConstants.ALGAE_DEFAULT_SPEED;
            }
        }

        if (algaeOuttakeState) {
            speed = AlgaeConstants.ALGAE_OUTTAKE_SPEED;
            algaeSubsystem.deployArm(false);
        }
        else {
            if (!algaeIntakeState) {
                speed = AlgaeConstants.ALGAE_DEFAULT_SPEED;
            }
        }

        algaeSubsystem.setIntakeSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

    }
}


