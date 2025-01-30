package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name="AutonAccentLockArm")
//@Disabled
public class AutonAccentLockArm extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    private DcMotorEx armMotor, slideMotor;
    private CRServo clawServo;
    double forward = 0.5;
    double back = -0.5;
    double timeToMoveOneInch = 31.34;
    double timeToMoveOneDegree = 6.15;
    double timeToMoveArmOneDegree = 9.0;
    double timeToMoveSliderOneInch = 35.5;
    public static double p = 0.003, i = 0.5, d = 1.5;
    public static double f = 0.015;
    public static int target;

    public final double ticks_in_degrees = 700 / 180.0;

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

//        flMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        frMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        blMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        brMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Reverse direction of motors
        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        slideMotor = hardwareMap.get(DcMotorEx.class, "slide");

        waitForStart();

        target = armMotor.getCurrentPosition();

        Runnable lockArm = () -> {
            telemetry.addData("ArmCode", "Waiting");
            telemetry.update();

            long startTime = System.currentTimeMillis();
            long duration = 26000;

            while (System.currentTimeMillis() - startTime < duration) {
                lockArm();
            }
        };

        Thread lockArmThread = new Thread(lockArm);
        lockArmThread.start();

        //go to block pushing position
        moveRobotLeft(4);
        moveRobotForward(50);

        //move three blocks
        moveRobotLeft(15);
        moveRobotBack(47);
        moveRobotForward(5);
        moveRobotRight(14);
        moveRobotBack(11);
        moveRobotLeft(20);
        moveRobotRight(5);
        moveRobotForward(51);
        moveRobotLeft(14);
        moveRobotBack(53);
        moveRobotForward(51);
        moveRobotLeft(11);
        moveRobotBack(53);

        //goto the parking in observation
        moveRobotForward(9);
        moveRobotRight( 141);
        moveRobotBack(13);
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
        flMotor.setPower(back);
        frMotor.setPower(forward);
        blMotor.setPower(forward);
        brMotor.setPower(back);
        sleep((long) robotRunTimeInMilliSeconds);
    }

    public void moveRobotRight(long inches) {
        //robot at 2.75 speed has to run for 3 millisecond to cover a inch
        double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;

        //set the motor power to 'power'
        flMotor.setPower(forward);
        frMotor.setPower(back);
        blMotor.setPower(back);
        brMotor.setPower(forward);
        sleep((long) robotRunTimeInMilliSeconds);
    }

    public void turnRobotLeft(long degrees) {
        double robotRunTimeInMilliSeconds = degrees * timeToMoveOneDegree;
        //set the motor power to 'power'
        flMotor.setPower(back);
        frMotor.setPower(forward);
        blMotor.setPower(back);
        brMotor.setPower(forward);
        sleep((long) robotRunTimeInMilliSeconds);
    }
    public void turnRobotRight(long degrees) {
        double robotRunTimeInMilliSeconds = degrees * timeToMoveOneDegree;
        //set the motor power to 'power'
        flMotor.setPower(forward);
        frMotor.setPower(back);
        blMotor.setPower(forward);
        brMotor.setPower(back);
        sleep((long) robotRunTimeInMilliSeconds);
    }

    public void moveRobotForward(long inches) {
        //robot at 2.75 speed has to run for 3 millisecond to cover a inch
        double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;
        telemetry.addData("forward value", forward);

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

    public void lockArm() {
        double ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * f;
        armMotor.setPower(ff);
    }

    public void moveArmUp(long degrees) {
        double robotRunTimeInMilliSeconds = degrees * timeToMoveArmOneDegree;;
        //set the motor power to 'power'
        armMotor.setPower(forward);
        sleep((long) robotRunTimeInMilliSeconds);
        armMotor.setPower(0);
      /*  long startTime = System.currentTimeMillis();
        long duration = (long) robotRunTimeInMilliSeconds;
        while (System.currentTimeMillis() - startTime < duration) {
            arm(-5);
        }*/
    }

    public void moveArmDown(long degrees) {
        double robotRunTimeInMilliSeconds = degrees * timeToMoveArmOneDegree;;
        //set the motor power to 'power'
        armMotor.setPower(back);
        sleep((long) robotRunTimeInMilliSeconds);
        armMotor.setPower(0);

        /*long startTime = System.currentTimeMillis();
        long duration = (long) robotRunTimeInMilliSeconds;

        while (System.currentTimeMillis() - startTime < duration) {
            arm(5);
        }*/
    }

    public void moveSliderOut(long inches) {
        double robotRunTimeInMilliSeconds = inches * timeToMoveSliderOneInch;;
        //set the motor power to 'power'
        slideMotor.setPower(forward);
        sleep((long) robotRunTimeInMilliSeconds);
        slideMotor.setPower(0);
    }

    public void moveSliderIn(long inches) {
        double robotRunTimeInMilliSeconds = inches * timeToMoveSliderOneInch;;
        //set the motor power to 'power'
        slideMotor.setPower(back);
        sleep((long) robotRunTimeInMilliSeconds);
        slideMotor.setPower(0);
    }

    public void arm(int inputPower) {
        int target = armMotor.getCurrentPosition();
        // PID control
        PIDController controller = new PIDController(p, i, d);
        controller.setPID(p, i, d);
        int armPosition = armMotor.getCurrentPosition();
        double pid = controller.calculate(armPosition, target);
        double ticks_in_degrees = 700 / 180.0;
        double ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * f;

        double armPower = pid + ff;

        target = target + inputPower * 3;
        armMotor.setPower(armPower);

        telemetry.addData("armPosition", armPosition);
        telemetry.addData("target", target);
        telemetry.addData("pid", armPower);
        telemetry.update();
    }
}
