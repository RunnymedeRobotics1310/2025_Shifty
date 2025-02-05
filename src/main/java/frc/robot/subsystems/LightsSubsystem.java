package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;
import frc.robot.Robot;

public class LightsSubsystem extends SubsystemBase {

    private final AddressableLED              ledString               = new AddressableLED(LightsConstants.LED_STRING_PWM_PORT);
    private final AddressableLEDBuffer        ledBuffer               = new AddressableLEDBuffer(
        LightsConstants.LED_STRING_LENGTH);

    private final AddressableLEDBufferView    leftSpeedBuffer         = new AddressableLEDBufferView(ledBuffer, 1, 28);
    private final AddressableLEDBufferView    rightSpeedBuffer        = new AddressableLEDBufferView(ledBuffer, 31, 58)
        .reversed();

    // FIXME: do these lights work? You may need to put them at 0, and 1, and shorten the speed
    // buffers.
    private final AddressableLEDBufferView    algaeIntakeLightBuffer  = new AddressableLEDBufferView(ledBuffer, 27, 28);
    private final AddressableLEDBufferView    algaeOuttakeLightBuffer = new AddressableLEDBufferView(ledBuffer, 25, 26);

    // RSL Flash
    private static final Color                RSL_COLOR               = new Color(255, 20, 0);
    private static final AddressableLEDBuffer RSL_ON                  = new AddressableLEDBuffer(
        LightsConstants.LED_STRING_LENGTH);
    private static final AddressableLEDBuffer RSL_OFF                 = new AddressableLEDBuffer(
        LightsConstants.LED_STRING_LENGTH);
    private Timer                             simRslTimer             = new Timer();
    private boolean                           simRslState             = false;
    private int                               rslFlashCount           = -1;
    private boolean                           previousRslState        = false;

    public LightsSubsystem() {

        // Initialize the RSL flash buffers
        LEDPattern.solid(RSL_COLOR).applyTo(RSL_ON);
        LEDPattern.kOff.applyTo(RSL_OFF);

        // Start the LED string
        ledString.setLength(LightsConstants.LED_STRING_LENGTH);
        ledString.start();
    }

    public void setDriveMotorSpeeds(double leftSpeed, double rightSpeed) {

        LEDPattern.kOff.applyTo(leftSpeedBuffer);
        LEDPattern.kOff.applyTo(rightSpeedBuffer);

        setSpeedPixel(leftSpeed, leftSpeedBuffer);
        setSpeedPixel(rightSpeed, rightSpeedBuffer);

    }

    // FIXME: add the piston deploy state
    public void setAlgaeIntakeLights(double intakeSpeed) {

        // FIXME: maybe there are 3 states, > 0 = intake, < 0 = outtake, 0 = stopped

        if (intakeSpeed == 0.5) {

            // FIXME: you can use the LEDPattern method for this. The loop still works but this is
            // cleaner
            // LEDPattern.solid(Color.kOrangeRed).applyTo(algaeOuttakeLightBuffer);
            for (int i = 0; i < algaeOuttakeLightBuffer.getLength(); i++) {
                algaeOuttakeLightBuffer.setLED(i, Color.kOrangeRed);
            }
        }
        // FIXME: the algaeOuttakeLightBuffer never gets turned off.

        // FIXME: do you need a Math.abs() here (intake and outtake will go in different directions)
        if (intakeSpeed >= 0.95) {
            for (int i = 0; i < algaeIntakeLightBuffer.getLength(); i++) {
                algaeIntakeLightBuffer.setLED(i, Color.kTurquoise);
            }
        }

        else {
            LEDPattern.kOff.applyTo(algaeIntakeLightBuffer);
            // FIXME: if you want to make sure this is called, it is better to use another
            // colour other than the default. Maybe a light grey?
        }
    }

    private void setSpeedPixel(double speed, AddressableLEDBufferView speedBuffer) {

        int center     = speedBuffer.getLength() / 2;
        int speedPixel = 0;

        if (speed == 0) {

            speedBuffer.setLED(center, Color.kBeige);

        }
        else if (speed > 0) {

            speedPixel = center + (int) Math.round(center * speed + 0.5);
            speedPixel = Math.min(speedBuffer.getLength() - 1, speedPixel);

            speedBuffer.setLED(speedPixel, Color.kLawnGreen);
        }
        else {

            speedPixel = center + (int) Math.round(center * speed - 0.5);
            speedPixel = Math.max(0, speedPixel);

            speedBuffer.setLED(speedPixel, Color.kFirstRed);
        }

    }

    public void setRSLFlashCount(int count) {
        rslFlashCount = count;
    }

    @Override
    public void periodic() {

        if (rslFlashCount > 0) {
            flashRSL();
        }
        else {

            // Update the LEDs on the corners to flash the RSL color
            if (getRSLState()) {
                ledBuffer.setLED(0, RSL_COLOR);
                ledBuffer.setLED(29, RSL_COLOR);
                ledBuffer.setLED(30, RSL_COLOR);
                ledBuffer.setLED(59, RSL_COLOR);
            }
            else {
                ledBuffer.setLED(0, Color.kBlack);
                ledBuffer.setLED(29, Color.kBlack);
                ledBuffer.setLED(30, Color.kBlack);
                ledBuffer.setLED(59, Color.kBlack);
            }

            // Apply the total view to the LEDs
            ledString.setData(ledBuffer);
        }
    }

    @Override
    public void simulationPeriodic() {

        // Set the simulated RSL state
        if (RobotState.isEnabled()) {
            simRslTimer.start();
            if (simRslTimer.hasElapsed(.5)) {
                simRslState = !simRslState; // toggle the state ever .5 sec
                simRslTimer.restart();
            }
        }
        else {
            // Solid on when not enabled
            simRslState = true;
        }
    }

    private boolean getRSLState() {

        // Simulate the rsl flash if in simulation
        if (Robot.isSimulation()) {
            return simRslState;
        }
        return RobotController.getRSLState();
    }

    /**
     * Flash all LEDs in the buffer in time with the RSL light for a set number of flashes
     */
    private void flashRSL() {

        boolean rslState = getRSLState();

        // when the RSL goes from on to off, decrement the flash count
        if (!rslState && previousRslState) {
            rslFlashCount--;
        }
        previousRslState = rslState;

        if (rslState) {
            ledString.setData(RSL_ON);
        }
        else {
            ledString.setData(RSL_OFF);
        }
    }
}
