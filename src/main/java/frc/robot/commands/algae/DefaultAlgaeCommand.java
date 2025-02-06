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


    // FIXME: the algaeIntakeState should not be passed to this command.
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

        // FIXME: What should happen when both buttons are pressed - I think maybe nothing.
        // So if you push one side first, and then push the other side,
        // maybe the first action continues until you let go of one of them
        // ie, the state should not change?
        //
//        if (operatorInput.intakeAlgae() && operatorInput.outtakeAlgae()) {
//            // Do nothing - do not change the piston or the motor state.
//            return;
//        }
//
        // FIXME: Maybe there is an easier way to do all of this logic below
        // There are 3 states, intake, outtake, or none.

        // If (intake) {
        // ...do intake stuff
        // else if (outtake)
        // ...do outtake stuff
        // else
        // ...go to the resting state (none)
        //

        algaeIntakeState  = false;
        algaeOuttakeState = false;
        speed             = 0;

        if (operatorInput.intakeAlgae()) {
            algaeIntakeState  = true;
            // FIXME: this is not required because it was done above
            algaeOuttakeState = false;

        }

        if (operatorInput.outtakeAlgae()) {
            // FIXME: this line was done at the top
            algaeIntakeState  = false;
            algaeOuttakeState = true;
        }

        // FIXME: This should not be required if we do nothing
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


