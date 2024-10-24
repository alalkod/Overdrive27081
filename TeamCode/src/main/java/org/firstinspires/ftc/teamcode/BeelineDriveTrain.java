package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="Beeline Drive Train")
public class BeelineDriveTrain extends LinearOpMode {
    private DcMotor flMotor, frMotor;
    private DcMotor armMotor;
    private CRServo wristServo, intakeServo;
    private float yForce, yaw, divisor;
    private float armForce;
    private double armPosition;
    private double armFinalPos;
    private float wristForce, intakeForce;

    // Drive code
    public void drive() {
        yForce = gamepad1.left_stick_y;
        yaw = gamepad1.right_stick_x;

        divisor = Math.max(Math.abs(yForce) + Math.abs(yaw), 1);

        // forward/backward
        flMotor.setPower((yForce + yaw) / divisor);
        frMotor.setPower((-yForce + yaw) / divisor);
    }

    public void arm() {
        // TODO: use PIDF loop to prevent arm from falling from gravity
        armForce = gamepad2.left_stick_y;
        armPosition = armMotor.getCurrentPosition();
        wristForce = gamepad2.right_stick_x;
        intakeForce = gamepad2.left_trigger + -gamepad2.right_trigger;

        armFinalPos = armForce * 10 + armPosition;

        telemetry.addData("Arm Encoder Reading", armPosition);
        telemetry.addData("Arm Target Position", armFinalPos);

        armMotor.setTargetPosition((int) armFinalPos);
        armMotor.setPower(0.5);
        wristServo.setPower(wristForce);
        intakeServo.setPower(intakeForce);
    }

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Waiting");
        telemetry.update();

        // Initialization
        flMotor = hardwareMap.get(DcMotor.class, "fl");
        frMotor = hardwareMap.get(DcMotor.class, "fr");
        armMotor = hardwareMap.get(DcMotor.class, "arm");
        wristServo = hardwareMap.get(CRServo.class, "wrist");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        // Prevent arm from falling
//        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setTargetPosition(armMotor.getCurrentPosition());
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            telemetry.update();
        }
    }
}
