package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="Mechanum Drive Code PID")
public class MechanumDriveCodePID extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    private DcMotorEx slideMotor, armMotor;
    private CRServo sampleIntakeServo;
    private Servo leftSpecimenIntakeServo, rightSpecimenIntakeServo;

    public static double p = 0.015, i = 0.02, d = 0.001;
    public static double f = 0;
    public static double target;

    // Slide and rotation of slide code
    // TODO: implement PID/encoder system on arm to prevent it from "falling"
    public void arm() {
        // PID control
        PIDController controller = new PIDController(p, i, d);
        controller.setPID(p, i, d);
        int armPosition = armMotor.getCurrentPosition();
        double pid = controller.calculate(armPosition, target);
        double ticks_in_degrees = 700 / 180.0;
        double ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * f;
        double armPower = pid + ff;

        target +=  gamepad2.right_stick_y;
        armMotor.setPower(armPower);

        telemetry.addData("armPosition", armPosition);
        telemetry.addData("target", target);
        telemetry.addData("pid", pid);
    }

    public void slide() {
        double slidePower = 0.6 * gamepad2.left_stick_y;
        slideMotor.setPower(slidePower);
    }

    // Intakes separated from arm because of future PID implementation on arm
    public void sampleIntake() {
        double sampleIntakePower = gamepad2.right_trigger - gamepad2.left_trigger;
        sampleIntakeServo.setPower(sampleIntakePower);
    }

    public void specimenIntake() {
        if(gamepad1.a) {
            rightSpecimenIntakeServo.setPosition(1);
            leftSpecimenIntakeServo.setPosition(0);
        }

        if(gamepad1.b) {
            rightSpecimenIntakeServo.setPosition(0);
            leftSpecimenIntakeServo.setPosition(1);

        }
        double leftSpecimenIntakeServoPosition = leftSpecimenIntakeServo.getPosition();
        double rightSpecimenIntakeServoPosition = rightSpecimenIntakeServo.getPosition();
        telemetry.addData("leftSpecimenIntakeServoPosition", leftSpecimenIntakeServoPosition);
        telemetry.addData("rightSpecimenIntakeServoPosition", rightSpecimenIntakeServoPosition);
    }

    // Drive code
    public void drive() {
        double xPower = 0.7 * gamepad1.left_stick_x;
        double yPower = 0.7 * -gamepad1.left_stick_y;
        double yaw = 0.7 * gamepad1.right_stick_x;

        double divisor = Math.max(Math.abs(xPower) + Math.abs(yPower) + Math.abs(yaw), 1);

        flMotor.setPower((xPower + yPower + yaw) / divisor);
        frMotor.setPower((-xPower + yPower - yaw) / divisor);
        blMotor.setPower((-xPower + yPower + yaw) / divisor);
        brMotor.setPower((xPower + yPower - yaw) / divisor);
    }

    public void initialization() {
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

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftSpecimenIntakeServo = hardwareMap.get(Servo.class, "intakeServoLeft");
        rightSpecimenIntakeServo = hardwareMap.get(Servo.class, "intakeServoRight");

        sampleIntakeServo = hardwareMap.get(CRServo.class, "intakeServoClaw");
    }

    public void runOpMode() {
        // init
        initialization();

        // create multiple telemetries and add to dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status", "Waiting");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            this.slide();
            this.specimenIntake();
            this.sampleIntake();

            telemetry.update();
        }
    }
}