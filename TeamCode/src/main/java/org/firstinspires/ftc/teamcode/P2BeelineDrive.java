package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="P2 Beeline Drive Train")
public class P2BeelineDrive extends LinearOpMode {
    private DcMotor flMotor, frMotor;
    private DcMotor armMotor;
    private CRServo wristServo, intakeServo;
    private float yForce, yaw, divisor;
    private float armForce;
    private float wristForce, intakeForce;

    // Drive code
    public void drive() {
        c    }

    public void arm() {
        armForce = gamepad2.left_stick_y;
        wristForce = gamepad2.right_stick_x;
        intakeForce = gamepad2.left_trigger + -gamepad2.right_trigger;

        armMotor.setPower(armForce);
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
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
        }
    }
}
