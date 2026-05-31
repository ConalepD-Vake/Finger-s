package OPMODES;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import SubSystems.DriveTrain.wheels;
import SubSystems.DriveTrain.intake;
import SubSystems.DriveTrain.shooter;


@TeleOp(name = "Finger's TeleOp", group = "TeleOp")
public class teleOp extends LinearOpMode {

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {

        wheels drive = new wheels(hardwareMap);
        shooter Shooter = new shooter(hardwareMap);
        intake Intake = new intake(hardwareMap);

        DcMotor chassis1 = hardwareMap.get(DcMotorEx.class, "chassis1");
        DcMotor chassis2 = hardwareMap.get(DcMotorEx.class, "chassis2");

        chassis1.setDirection(DcMotorEx.Direction.FORWARD);
        chassis2.setDirection(DcMotorEx.Direction.REVERSE);

        drive.init();
        Intake.init();
        Shooter.init();



        waitForStart();
        Intake.activateIntaker();

        while (opModeIsActive()) {

            double lead = gamepad1.right_trigger - gamepad1.left_trigger;
            double giro = gamepad1.left_stick_x;
            double leftPower = Math.max(-1.0, Math.min(1.0, lead + giro));
            double rightPower = Math.max(-1.0, Math.min(1.0, lead - giro));


            if (gamepad2.b) {
                Shooter.shoot();
            }

            if (gamepad2.a) {
                Shooter.stopShooter();
            }

            if (gamepad2.right_bumper) {
                Shooter.antijam();
            }

            if (gamepad2.left_bumper)
                Intake.activateIntaker();

            if (gamepad2.x) {
                Intake.stopIntake();
            }

            if (gamepad2.y) {
                Intake.normalIntake();
            } else {
                Intake.invertIntake();
            }

        }
    }
}