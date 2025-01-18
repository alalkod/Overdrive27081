// Not working!

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Mechanum Drive Code")
public class MechanumDriveCode extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    private double xPower, yPower, yaw, divisor;
    private DcMotorEx slideMotor, armMotor;
    private double slidePower, armPower;
    private double fineStrafePower;
    private CRServo sampleIntakeServo;
    private double sampleIntakePower;
    private CRServo leftSpecimenIntakeServo;
    private CRServo rightSpecimenIntakeServo;
    private double specimenIntakePower;

    // Slide and rotation of slide code
    // TODO: implement PID/encoder system on arm to prevent it from "falling"
    public void arm() {
        armPower = gamepad2.right_stick_y;
        armMotor.setPower(0.6 * armPower);

//        fineStrafePower = gamepad2.right_trigger - gamepad2.left_trigger;
//
//        flMotor.setPower(fineStrafePower);
//        frMotor.setPower(-fineStrafePower);
//        blMotor.setPower(-fineStrafePower);
//        brMotor.setPower(fineStrafePower);
    }

    public void slide() {
        slidePower = 0.6 * gamepad2.left_stick_y;

        slideMotor.setPower(slidePower);
    }

    // Intakes separated from arm because of future PID implementation on arm
    public void sampleIntake() {
        sampleIntakePower = gamepad2.right_trigger - gamepad2.left_trigger;

        sampleIntakeServo.setPower(sampleIntakePower);
    }

    public void specimenIntake() {
        specimenIntakePower = gamepad1.right_trigger - gamepad1.left_trigger;

        leftSpecimenIntakeServo.setPower(specimenIntakePower);
        rightSpecimenIntakeServo.setPower(-specimenIntakePower);
    }

    // Drive code
    public void drive() {
        xPower = 0.7 * gamepad1.left_stick_x;
        yPower = 0.7 * -gamepad1.left_stick_y;
        yaw = 0.7 * gamepad1.right_stick_x;

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

        slideMotor = hardwareMap.get(DcMotorEx.class, "slide");
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        sampleIntakeServo = hardwareMap.get(CRServo.class, "intakeServoClaw");
        leftSpecimenIntakeServo = hardwareMap.get(CRServo.class, "intakeServoLeft");
        rightSpecimenIntakeServo = hardwareMap.get(CRServo.class, "intakeServoRight");

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            this.slide();
            this.sampleIntake();
            this.specimenIntake();

            telemetry.update();
        }
    }
}