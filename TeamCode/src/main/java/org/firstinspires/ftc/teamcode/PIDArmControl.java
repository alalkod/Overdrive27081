package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Telemetry/dashboard
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;

@Config
@TeleOp(name="PID Arm Control")
public class PIDArmControl extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    float xPower, yPower, yaw, divisor;
    private DcMotorEx slideMotor, slideRotationMotor;
    float slidePower, slideRotationPower;

    // PID
    PIDController controller;
    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    double pid, ff;

    double slideRotatedTicks;
    public static double slideTargetRotatedTicks;
    final double ticksInDegrees = 700 / 180.0f;

    // Slide and rotation of slide code
    public void viper_slide() {
        // get slide rotation info
        slideRotatedTicks = slideRotationMotor.getCurrentPosition();

        // PID loop
        controller.setPID(p, i, d);
        pid = controller.calculate(slideRotatedTicks, slideTargetRotatedTicks);
        ff = Math.cos(Math.toRadians(slideTargetRotatedTicks / ticksInDegrees)) * f;

        slideRotationPower = (float) pid + (float) ff;
        slideRotationMotor.setPower(slideRotationPower);

        telemetry.addData("slideRotatedTicks", slideRotatedTicks);
        telemetry.addData("slideTargetRotatedTicks", slideTargetRotatedTicks);
    }

    // Drive code
    public void drive() {
        xPower = gamepad1.left_stick_x;
        yPower = -gamepad1.left_stick_y;
        yaw = gamepad1.right_stick_x;

        divisor = Math.max(Math.abs(xPower) + Math.abs(yPower) + Math.abs(yaw), 1);

        flMotor.setPower((xPower + yPower + yaw) / divisor);
        frMotor.setPower((-xPower + yPower - yaw) / divisor);
        blMotor.setPower((-xPower + yPower + yaw) / divisor);
        brMotor.setPower((xPower + yPower - yaw) / divisor);
    }

    public void runOpMode() {
        // init

        // define controller
        controller = new PIDController(p, i, d);

        // create multiple telemetries and add to dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("Status", "Waiting");
        telemetry.update();

        // Initialization
        flMotor = hardwareMap.get(DcMotorEx.class, "fl");
        frMotor = hardwareMap.get(DcMotorEx.class, "fr");
        blMotor = hardwareMap.get(DcMotorEx.class, "bl");
        brMotor = hardwareMap.get(DcMotorEx.class, "br");
        flMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slideRotationMotor = hardwareMap.get(DcMotorEx.class, "slideRotationMotor");
        slideRotationMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.viper_slide();

            telemetry.update();
        }
    }
}