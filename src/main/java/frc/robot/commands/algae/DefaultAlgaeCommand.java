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


    // FIXME: the default command will only take the oi as the first parameter, and the subsystem
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

        // FIXME: Intialize both states to false, and turn on the appropriate flag
        // algaeIntakeState = false;
        // algaeOuttakeState = false;
        // speed = 0;

        // FIXME: this check for 0.5 should be done in the operator input layer,
        // which should return a boolean value.
        if (operatorInput.startAlgaeIntake() >= 0.5) {
            algaeIntakeState  = true;
            algaeOuttakeState = false;
        }
        // FIXME: should be initialized to false above.
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

        // FIXME: Write the positive condition first, and then an else condition.
        if (!algaeIntakeState) {
            speed = 0;
            algaeSubsystem.setShift(false);
            // FIXME: do not log in the execute method.
            log("Algae OFF");
        }

        if (algaeIntakeState) {
            algaeSubsystem.setShift(true);
            // FIXME: constant?
            speed = 1;

            log("Algae O");
        }


        // FIXME: write the positive logic first, and use an else.
        // Do not log in the execute loop.
        if (!algaeOuttakeState) {
            log("Outtake OFF");
        }

        else {
            // FIXME: Should the outtake roll in a different direction?
            // FIXME: make the intake and outtake speed constants in the constants file.
            speed = 0.5;
            log("Outtake O");
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


