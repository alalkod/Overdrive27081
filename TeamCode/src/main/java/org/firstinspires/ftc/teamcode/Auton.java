package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Auton extends LinearOpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        leftMotor = hardwareMap.dcMotor.get("fl");
        rightMotor = hardwareMap.dcMotor.get("fr");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        boolean update1 = telemetry.update();

        waitForStart();

        telemetry.addData("Mode", "running");

        sleep(500);

        this.moveRobotToObservationArea();

    }

    public void moveRobotToObservationArea(){
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(1000); //forward from start

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(-0.5);
        rightMotor.setPower(0.5);

        sleep(1000); //turning towards the baskets

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(3000); //towards baskets

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(-0.5);
        rightMotor.setPower(0.5);

        sleep(500); //adjusting towards basket

        this.putBlockInBasket();
    }
    public void putBlockInBasket(){

        //Add the arm code for placing blocks into basket

        this.moveRobotToBlocks();

    }
    public void moveRobotToBlocks(){

        leftMotor.setPower(-0.5);
        rightMotor.setPower(0.5);

        sleep(1500); //turn Robot towards the blocks positioning

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(4000); //going towards blocks for second pickup

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        this.pickupBlocksFromFloor();
    }
    public void pickupBlocksFromFloor(){

        //Add code to pickup block from floor

        this.moveRobotToObservationArea2();
    }
    public void moveRobotToObservationArea2(){

        leftMotor.setPower(-0.5);
        rightMotor.setPower(0.5);

        sleep(1000); //Turning towards the Observation Area

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(2000); //Going towards the Observation Area

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        this.putBlockInBasket2();


    }
    public void putBlockInBasket2(){

        //Put Block In Basket Arm Code

        this.moveRobotToAscentArea();

    }
    public void moveRobotToAscentArea(){

        leftMotor.setPower(-0.5);
        rightMotor.setPower(0.5);

        sleep(1000); //turn towards Ascent Area

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(4000); //Move towards Acsent Zone

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //rest

        leftMotor.setPower(0.5);
        rightMotor.setPower(-0.5);

        sleep(500); //turn to park

        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);

        sleep(200); //Parked!!

    }
}