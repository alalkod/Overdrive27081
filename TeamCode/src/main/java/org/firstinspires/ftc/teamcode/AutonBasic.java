package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;




    @Autonomous(name = "AutonBaic", group = "Autonomous")
//@Disabled
    public class AutonBasic extends LinearOpMode {

        private DcMotorEx flMotor, frMotor, blMotor, brMotor, slideMotor, armMotor;
        private CRServo clawServo;
        @Override
        public void runOpMode() throws InterruptedException {
            // create multiple telemetries and add to dashboard
            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

            telemetry.addData("Status", "Waiting");
            telemetry.update();

            // Initialization
            flMotor = hardwareMap.get(DcMotorEx.class, "fl");
            frMotor = hardwareMap.get(DcMotorEx.class, "fr");
            blMotor = hardwareMap.get(DcMotorEx.class, "bl");
            brMotor = hardwareMap.get(DcMotorEx.class, "br");
            slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
            armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
            clawServo = hardwareMap.get(CRServo.class, "clawServo");


            waitForStart();
            //while (opModeIsActive()) {
                //move robot 26 inches forward from the parked spot
                moveRobotLeft(50);
                moveRobotRight(25);
                moveRobotForward(12);
                moveRobotBack(6);
                stopRobot();
           // }
        }

        public void stopRobot() {
            flMotor.setPower(0);
            frMotor.setPower(0);
            blMotor.setPower(0);
            brMotor.setPower(0);
        }

        public void moveRobotLeft(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double forward = 0.25;
            double back = -0.25;
            long robotRunTimeInMilliSeconds = inches * 100;
            ;
            //set the motor power to 'power'
            flMotor.setPower(forward);
            frMotor.setPower(back);
            blMotor.setPower(back);
            brMotor.setPower(forward);
            sleep(robotRunTimeInMilliSeconds);
        }

        public void moveRobotRight(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double forward = 0.25;
            double back = -0.25;
            long robotRunTimeInMilliSeconds = inches * 100;
            ;
            //set the motor power to 'power'
            flMotor.setPower(back);
            frMotor.setPower(forward);
            blMotor.setPower(forward);
            brMotor.setPower(back);
            sleep(robotRunTimeInMilliSeconds);
        }

        public void moveRobotForward(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double forward = 0.25;
            double back = -0.25;
            long robotRunTimeInMilliSeconds = inches * 100;
            ;
            //set the motor power to 'power'
            flMotor.setPower(forward);
            frMotor.setPower(forward);
            blMotor.setPower(forward);
            brMotor.setPower(forward);
            sleep(robotRunTimeInMilliSeconds);
        }

        public void moveRobotBack(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double forward = 0.25;
            double back = -0.25;
            long robotRunTimeInMilliSeconds = inches * 100;
            ;
            //set the motor power to 'power'
            flMotor.setPower(back);
            frMotor.setPower(back);
            blMotor.setPower(back);
            brMotor.setPower(back);
            sleep(robotRunTimeInMilliSeconds);
        }

    }
