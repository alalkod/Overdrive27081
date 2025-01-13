package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "AutonObservation", group = "Autonomous")
//@Disabled
public class AutonObservation extends LinearOpMode {

    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
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

        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        moveRobotForward(23);

        telemetry.addData("Move Forward 23 Inches", "Moving");
        telemetry.update();

        moveRobotLeft(69);

        telemetry.addData("Move Left 46 Inches", "Moving");
        telemetry.update();

        moveRobotForward(23);

        telemetry.addData("Move Forward 23 Inches", "Moving");
        telemetry.update();

        moveRobotLeft(13);

        telemetry.addData("Move Left 10 Inches", "Moving");
        telemetry.update();

        moveRobotBack(55);

        telemetry.addData("Move Back 47 Inches", "Moving");
        telemetry.update();

        moveRobotForward(10);

        moveRobotRight(10);

        moveRobotBack(13);

        moveRobotLeft(10);

        moveRobotRight(10);

        moveRobotForward(45);

        telemetry.addData("Move Forward 47 Inches", "Moving");
        telemetry.update();

        moveRobotLeft(21);

        telemetry.addData("Move Left 10 Inches", "Moving");
        telemetry.update();

        moveRobotBack(47);

        telemetry.addData("Move Back 47 Inches", "Moving");
        telemetry.update();

        moveRobotForward(10);

        telemetry.addData("Move Forward 47 Inches", "Moving");
        telemetry.update();

        moveRobotRight(10);

        telemetry.addData("Move Right 20 Inches", "Moving");
        telemetry.update();

        moveRobotForward(15);
        moveRobotRight(115);

        moveRobotBack(17);

        stopRobot();

        telemetry.addData("Stop Robot", "Stopped");
        telemetry.update();
    }

    public void stopRobot() {
        flMotor.setPower(0);
        frMotor.setPower(0);
        blMotor.setPower(0);
        brMotor.setPower(0);
    }

    public void moveRobotRight(long inches) {
        double robotRunTimeInMilliSeconds = inches * timeToMoveOneInch;

        //set the motor power to 'power'
        flMotor.setPower(forward);
        frMotor.setPower(back);
        blMotor.setPower(back);
        brMotor.setPower(forward);
        sleep((long) robotRunTimeInMilliSeconds);
    }

    public void moveRobotLeft(long inches) {
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
