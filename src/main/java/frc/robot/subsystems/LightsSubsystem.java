package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // Note: on Shifty, the lights are Y-ed, both of the strips run the same pattern.

    private final AddressableLED              ledStrip;
    private final AddressableLEDBuffer        ledBuffer;
    private static final AddressableLEDBuffer RSL_ON;
    private static final AddressableLEDBuffer RSL_OFF;
    private static final Color                RSL_COLOR     = new Color(255, 20, 0);

    private int                               rslFlashCount = -1;
    private boolean                           prevRslState  = false;

    static {
        RSL_ON  = new AddressableLEDBuffer(LightsConstants.LIGHT_STRING_LENGTH);
        RSL_OFF = new AddressableLEDBuffer(LightsConstants.LIGHT_STRING_LENGTH);

        for (int i = 0; i < LightsConstants.LIGHT_STRING_LENGTH; i++) {
            RSL_ON.setLED(i, RSL_COLOR);
            RSL_OFF.setLED(i, Color.kBlack);
        }
    }

    /** Creates a new DriveSubsystem. */
    public LightsSubsystem() {

        ledStrip  = new AddressableLED(LightsConstants.LIGHT_STRING_PWM_PORT);
        ledBuffer = new AddressableLEDBuffer(LightsConstants.LIGHT_STRING_LENGTH);

        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);

        ledStrip.start();
    }


    @Override
    public void periodic() {

        if (rslFlashCount >= 0) {
            flashRSL();
        }
        else {
            ledStrip.setData(ledBuffer);
        }
    }

    private void flashRSL() {

        boolean rslState = RobotController.getRSLState();

        // when the RSL goes from on to off, decrement the flash count
        if (!rslState && prevRslState) {
            rslFlashCount--;
        }
        prevRslState = RobotController.getRSLState();

        if (rslState) {
            ledStrip.setData(RSL_ON);
        }
        else {
            ledStrip.setData(RSL_OFF);
        }
    }


    public void setEnabled() {
        // Flash the RSL light when the robot is enabled
        rslFlashCount = 5;
    }
}
