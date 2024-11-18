package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.GameController;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends LoggingCommand {

    private final DriveSubsystem             driveSubsystem;
    private final XboxController             driverController;
    private final SendableChooser<DriveMode> driveModeChooser;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(GameController driverController, SendableChooser<DriveMode> driveModeChooser,
        DriveSubsystem driveSubsystem) {

        this.driverController = driverController;
        this.driveModeChooser = driveModeChooser;
        this.driveSubsystem   = driveSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        DriveMode driveMode = driveModeChooser.getSelected();

        boolean   boost     = driverController.getRightBumper();
        boolean   shift     = driverController.getLeftBumper();

        SmartDashboard.putBoolean("Boost", boost);

        switch (driveMode) {

        case DUAL_STICK_ARCADE:
            setMotorSpeedsArcade(driverController.getLeftY(), driverController.getRightX(), boost);
            break;

        case SINGLE_STICK_ARCADE:
            setMotorSpeedsArcade(driverController.getLeftY(), driverController.getLeftX(), boost);
            break;

        case TANK:
        default:

            if (boost) {
                driveSubsystem.setMotorSpeeds(driverController.getLeftY(), driverController.getRightY());
            }
            else {
                // If not in boost mode, then divide the motors speeds in half
                driveSubsystem.setMotorSpeeds(driverController.getLeftY() / 2.0, driverController.getRightY() / 2.0);
            }
            break;
        }

        driveSubsystem.setShift(shift);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        SmartDashboard.putBoolean("Boost", false);
    }

    private void setMotorSpeedsArcade(double speed, double rawTurn, boolean boost) {

        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Turn", rawTurn);

        double  turn      = rawTurn / 2;
        // boolean boost = driverController.isBoost();
        boolean slow      = false;            // driverController.isSlowDown();

        double  leftSpeed = 0, rightSpeed = 0;

        if (slow) {
            speed = speed / 5;
            turn  = turn / 2.5; // Turn was already divided by 2 above
        }

        else if (!boost) {
            speed = speed / 2;
        }

        else {
            speed = Math.signum(speed);
        }

        if (speed < 0) {
            turn = -turn;
        }

        if (speed == 0) {
            leftSpeed  = turn;
            rightSpeed = -turn;
        }
        else if (boost) {

            // Turning left
            if (rawTurn < 0) {

                // If boosted and at full speed, and the
                // turn is limited to 0.5, then the max turn
                // that can be achieved is 1.0 vs 0.5.
                leftSpeed  = speed + turn;
                rightSpeed = speed;
            }
            // Turning right
            else if (rawTurn > 0) {
                leftSpeed  = speed;
                rightSpeed = speed - turn;
            }
            else {
                leftSpeed  = speed;
                rightSpeed = speed;
            }
        }
        else {
            if (rawTurn < 0) {
                leftSpeed  = speed;
                rightSpeed = speed - turn;
            }
            else if (rawTurn > 0) {
                leftSpeed  = speed + turn;
                rightSpeed = speed;
            }
            else {
                rightSpeed = speed;
                leftSpeed  = speed;
            }
        }
        driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
    }


}