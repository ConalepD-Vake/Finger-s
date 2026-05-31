    package OPMODES;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorEx;

    import SubSystems.DriveTrain.intake;
    import SubSystems.DriveTrain.shooter;
    @Autonomous(
            name = "Finger's Auto",
            group = "Auto"
    )

    public class auto extends LinearOpMode {

        private DcMotorEx chassis1;
        private DcMotorEx chassis2;

        @Override
        public void runOpMode() {

            chassis1 = hardwareMap.get(DcMotorEx.class, "chassis1");
            chassis2 = hardwareMap.get(DcMotorEx.class, "chassis2");


            chassis1.setDirection(DcMotor.Direction.FORWARD);
            chassis2.setDirection(DcMotor.Direction.REVERSE);

            chassis1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            chassis2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            intake Intaker = new intake(hardwareMap);

            shooter shooter = new shooter(hardwareMap);

            Intaker.init();
            shooter.init();

            waitForStart();

            if (opModeIsActive()) {
                // Loop Shoot
                goback();

                sleep(3000);

                smoothStop();



                shooter.shoot();
                sleep(15000);

                Intaker.activateIntaker();

                sleep(5000);



                Intaker.stopIntake();
                shooter.stopShooter();

                sleep(3000);

                stopMotors();
            }
        }
        private void stopMotors(){
            chassis1.setPower(0);
            chassis2.setPower(0);
        }

        private void smoothStop(){

            double power = 0.5;

            while(power > 0 && opModeIsActive()){

                chassis1.setPower(-power);
                chassis2.setPower(-power);

                power -= 0.08;

                sleep(20);
            }

            stopMotors();
        }

        private void go(){
            chassis1.setPower(0.5);
            chassis2.setPower(0.5);
        }

        private void goback(){
            chassis1.setPower(-0.5);
            chassis2.setPower(-0.5);
        }

    }