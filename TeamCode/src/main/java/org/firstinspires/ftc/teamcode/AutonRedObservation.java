package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;




    @Autonomous(name = "AutonRedObservation", group = "Autonomous")
//@Disabled
    public class AutonRedObservation extends LinearOpMode {

        private DcMotorEx flMotor, frMotor, blMotor, brMotor, slideMotor, armMotor;
        private CRServo clawServo;
        double forward = 0.5;
        double back = -0.5;
        double timeToMoveOneInch = 31.34;

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

            waitForStart();
            moveRobotLeft(30);
            stopRobot();
        }

        public void stopRobot() {
            flMotor.setPower(0);
            frMotor.setPower(0);
            blMotor.setPower(0);
            brMotor.setPower(0);
        }

        public void moveRobotLeft(long inches) {
            double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;

            //set the motor power to 'power'
            flMotor.setPower(forward);
            frMotor.setPower(back);
            blMotor.setPower(back);
            brMotor.setPower(forward);
            sleep((long) robotRunTimeInMilliSeconds);
        }

        public void moveRobotRight(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;

            //set the motor power to 'power'
            flMotor.setPower(back);
            frMotor.setPower(forward);
            blMotor.setPower(forward);
            brMotor.setPower(back);
            sleep((long) robotRunTimeInMilliSeconds);
        }

        public void moveRobotForward(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;

            //set the motor power to 'power'
            flMotor.setPower(forward);
            frMotor.setPower(forward);
            blMotor.setPower(forward);
            brMotor.setPower(forward);
            sleep((long) robotRunTimeInMilliSeconds);
        }

        public void moveRobotBack(long inches) {
            //robot at 2.75 speed has to run for 3 millisecond to cover a inch
            double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;
            ;
            //set the motor power to 'power'
            flMotor.setPower(back);
            frMotor.setPower(back);
            blMotor.setPower(back);
            brMotor.setPower(back);
            sleep((long) robotRunTimeInMilliSeconds);
        }

    }
