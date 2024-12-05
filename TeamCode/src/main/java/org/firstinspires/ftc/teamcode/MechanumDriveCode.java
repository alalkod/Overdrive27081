package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

// Telemetry/dashboard
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.arcrobotics.ftclib.controller.PIDController;

@Config
@TeleOp(name="Mechanum Drive Code")
public class MechanumDriveCode extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    float xPower, yPower, yaw, divisor;
    private DcMotorEx slideMotor, armMotor;
    float slidePower, armPower;
    private CRServo clawServo;
    float clawPower;

    // Slide and rotation of slide code
    // TODO: implement PID/encoder system on arm to prevent it from "falling"
    public void arm() {
        slidePower = (float) (0.6 * gamepad2.left_stick_y);
        armPower = (float) (0.4 * gamepad2.right_stick_y);

        slideMotor.setPower(slidePower);
        armMotor.setPower(armPower);
    }

    // Claw separated from arm because of future PID implementation on arm
    public void claw() {
        clawPower = gamepad2.right_trigger - gamepad2.left_trigger;

        clawServo.setPower(clawPower);
    }

    // Drive code
    public void drive() {
        xPower = (float) (0.7 * gamepad1.left_stick_x);
        yPower = (float) (0.7 * -gamepad1.left_stick_y);
        yaw = (float) (0.7 * gamepad1.right_stick_x);

        divisor = Math.max(Math.abs(xPower) + Math.abs(yPower) + Math.abs(yaw), 1);

        flMotor.setPower((xPower + yPower + yaw) / divisor);
        frMotor.setPower((-xPower + yPower - yaw) / divisor);
        blMotor.setPower((-xPower + yPower + yaw) / divisor);
        brMotor.setPower((xPower + yPower - yaw) / divisor);
    }

    public void runOpMode() {
        // init

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

        // Reverse direction of motors
        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        clawServo = hardwareMap.get(CRServo.class, "clawServo");

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            this.claw();

            telemetry.update();
        }
    }
}