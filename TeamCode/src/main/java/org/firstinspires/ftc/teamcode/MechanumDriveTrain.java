package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Mechanum Drive Train")
public class MechanumDriveTrain extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;

    // Drive code
    public void drive() {
        float xPower, yPower, yaw, divisor;

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
        telemetry.addData("Status", "Waiting");
        telemetry.update();

        // Initialization
        flMotor = hardwareMap.get(DcMotorEx.class, "fl");
        frMotor = hardwareMap.get(DcMotorEx.class, "fr");
        blMotor = hardwareMap.get(DcMotorEx.class, "bl");
        brMotor = hardwareMap.get(DcMotorEx.class, "br");

        waitForStart();

        while (opModeIsActive()) {
            this.drive();
        }
    }
}