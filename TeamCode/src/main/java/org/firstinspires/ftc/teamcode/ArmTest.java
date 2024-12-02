package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Arm Test")
public class ArmTest extends LinearOpMode {
    private DcMotorEx armMotor;

    public void runOpMode() {
        // init
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");

        waitForStart();

        while (opModeIsActive()) {
            armMotor.setPower(gamepad1.left_stick_y);
        }
    }
}
