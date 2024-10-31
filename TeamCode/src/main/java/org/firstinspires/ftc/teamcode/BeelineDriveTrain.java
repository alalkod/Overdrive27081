package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@Config
@TeleOp(name="Beeline Drive Train")
public class BeelineDriveTrain extends LinearOpMode {
    private DcMotor flMotor, frMotor;
    private DcMotorEx armMotor;
    private CRServo wristServo, intakeServo;

    // PID Controller
    PIDController controller;
    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    double pid, ff;

    double armPower, wristPower, intakePower;
    double armPosition;
    public static double armTargetPosition;
    final double ticksInDegrees = 700 / 180.0f;

    // Drive code
    public void drive() {
        float yForce, yaw, divisor;

        yForce = gamepad1.left_stick_y;
        yaw = gamepad1.right_stick_x;

        divisor = Math.max(Math.abs(yForce) + Math.abs(yaw), 1);

        // forward/backward
        flMotor.setPower((yForce + yaw) / divisor);
        frMotor.setPower((-yForce + yaw) / divisor);
    }

    public void arm() {
        controller.setPID(p, i, d);

        armPower = gamepad2.left_stick_y;
        armPosition = armMotor.getCurrentPosition();
        armTargetPosition = armPower * 10 + armPosition;

        // PID loop
        pid = controller.calculate(armPosition, armTargetPosition);
        ff = Math.cos(Math.toRadians(armTargetPosition / ticksInDegrees)) * f;

        armPower = pid + ff;

        armMotor.setPower(armPower);
        wristServo.setPower(wristPower);
        intakeServo.setPower(intakePower);

        telemetry.addData("armPosition", armPosition);
        telemetry.addData("armTargetPosition", armTargetPosition);
    }

    public void runOpMode() {
        // add telemetry to dashboard
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("Status", "Preparing");
        telemetry.update();

        // Initialization
        flMotor = hardwareMap.get(DcMotor.class, "fl");
        frMotor = hardwareMap.get(DcMotor.class, "fr");
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        wristServo = hardwareMap.get(CRServo.class, "wrist");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Ready");
        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            telemetry.update();
        }
    }
}
