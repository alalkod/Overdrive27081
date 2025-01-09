package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp(name="Mechanum Drive Code PID")
public class MechanumDriveCodePID extends LinearOpMode {
    private DcMotorEx flMotor, frMotor, blMotor, brMotor;
    private double xPower, yPower, yaw, divisor;
   private DcMotorEx slideMotor, armMotor;
    private double slidePower, armPower;
    private double fineStrafePower;
    private int armPosition;
    private CRServo intakeServoLeft,intakeServoRight,intakeServoClaw;

    private double intakePower;

    // PID control
    private PIDController controller;

    public static double p = 0.015, i = 0.02, d = 0.001;
    public static double f = 0;
    private double pid, ff;

    public static int target;

    private final double ticks_in_degrees = 700 / 180.0;

    // Slide and rotation of slide code
    // TODO: implement PID/encoder system on arm to prevent it from "falling"
    public void arm() {
        controller.setPID(p, i, d);

//        armPosition = armMotor.getCurrentPosition();

        pid = controller.calculate(armPosition, target);
        ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * f;

        armPower = pid + ff;

        target = (int) (target + gamepad2.right_stick_y * 5);
        armMotor.setPower(gamepad2.left_stick_y);

//        armMotor.setPower(armPower);

        fineStrafePower = gamepad1.right_stick_y;

        flMotor.setPower(fineStrafePower);
        frMotor.setPower(-fineStrafePower);
        blMotor.setPower(-fineStrafePower);
        brMotor.setPower(fineStrafePower);

        telemetry.addData("armPosition", armPosition);
        telemetry.addData("target", target);
    }

    public void slide() {
        slidePower = 0.6 * gamepad2.left_stick_y;

       slideMotor.setPower(slidePower);
    }

     //Claw separated from arm because of future PID implementation on arm
    public void intake() {
        intakePower = 0.5;
          if (gamepad2.a) {
              intakeServoLeft.setPower(intakePower);
          }
    }

    // Drive code
    public void drive() {
        xPower = 0.7 * gamepad1.left_stick_x;
        yPower = 0.7 * -gamepad1.left_stick_y;
        yaw = 0.7 * gamepad1.right_stick_x;

        divisor = Math.max(Math.abs(xPower) + Math.abs(yPower) + Math.abs(yaw), 1);

        flMotor.setPower((-xPower + yPower + yaw) / divisor*(4));
        frMotor.setPower((-xPower + yPower - yaw) / divisor*(4));
        blMotor.setPower((xPower + yPower + yaw) / divisor*(4));
        brMotor.setPower((xPower + yPower - yaw) / divisor*(4));
    }

    public void runOpMode() {
        // init

        controller = new PIDController(p, i, d);

        // create multiple telemetries and add to dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("Status", "Waiting");
        telemetry.update();

        // Initialization
        flMotor = hardwareMap.get(DcMotorEx.class, "fl");
        frMotor = hardwareMap.get(DcMotorEx.class, "fr");
        blMotor = hardwareMap.get(DcMotorEx.class, "bl");
        brMotor = hardwareMap.get(DcMotorEx.class, "br");

        flMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        intakeServoLeft = hardwareMap.get(CRServo.class, "intakeServoLeft");
        intakeServoRight = hardwareMap.get(CRServo.class, "intakeServoRight");
        intakeServoClaw = hardwareMap.get(CRServo.class, "intakeServoClaw");

        // Reverse direction of motors
        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();

        while (opModeIsActive()) {
            this.drive();
            this.arm();
            this.slide();
            this.intake();

            telemetry.update();
        }
    }
}