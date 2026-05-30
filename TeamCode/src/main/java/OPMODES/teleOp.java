package OPMODES;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import SubSystems.DriveTrain.pusher;
import SubSystems.DriveTrain.wheels;
import SubSystems.DriveTrain.intake;
import SubSystems.DriveTrain.shooter;


@SuppressWarnings("ALL")
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Finger's TeleOp", group = "TeleOp")
public class teleOp extends LinearOpMode {

    private wheels drive;
    private shooter disparador;
    private intake recolector;
    private DcMotor chassis1;
    private DcMotor chassis2;

    private pusher Empujador;
    boolean lastUp = false;
    boolean lastDown = false;

    private static final double DEADBAND = 0.05;

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {

        drive = new wheels(hardwareMap);
        disparador = new shooter(hardwareMap);
        recolector = new intake(hardwareMap);
        Empujador = new pusher(hardwareMap);
        boolean frenteInvertido = false;
        boolean ultimoEstadoY1 = false;

        chassis1 = hardwareMap.get(DcMotorEx.class, "chassis1");
        chassis2 = hardwareMap.get(DcMotorEx.class, "chassis2");

        chassis1.setDirection(DcMotorEx.Direction.FORWARD);
        chassis2.setDirection(DcMotorEx.Direction.REVERSE);

        drive.init();
        disparador.init();
        recolector.init();



        waitForStart();
        recolector.activarRecolector();

        while (opModeIsActive()) {

            double avance = gamepad1.right_trigger - gamepad1.left_trigger;
            double giro = gamepad1.left_stick_x;
            double potenciaIzquierda = Math.max(-1.0, Math.min(1.0, avance + giro));
            double potenciaDerecha = Math.max(-1.0, Math.min(1.0, avance - giro));


            if (gamepad2.b) {
                disparador.disparar();
            }

            if (gamepad2.a) {
                disparador.detener();
            }

            if (gamepad2.right_bumper) {
                disparador.antiAtasco();
            }

            if (gamepad2.left_bumper)
                recolector.activarRecolector();

            if (gamepad2.x) {
                recolector.detenerRecolector();
            }

            if (gamepad2.y) {
                recolector.normalRecolector();
            } else {
                recolector.invertirRecolector();
            }

            if(gamepad2.dpad_right){
                Empujador.activate();
            }

            if(gamepad2.dpad_left){
                Empujador.reverse();
            }

            Empujador.update();


            telemetry.addData("=== ESTADO DEL SISTEMA ===", "");
            telemetry.addData("Chasis Power Izq:", String.format("%.2f", potenciaIzquierda));
            telemetry.addData("Chasis Power Der:", String.format("%.2f", potenciaDerecha));
            telemetry.addData("Disparador Estado:", disparador.getEstado());
            telemetry.addData("Disparador Motor:", disparador.getMotorPower());

            telemetry.addData("=== RECOLECTOR ===", "");
            telemetry.addData("Recolector Power:", String.format("%.2f", recolector.getRecolectorPower()));
            telemetry.addData("Estado Recolector:", recolector.getEstado());
            telemetry.addData("Recolectando:", recolector.isRecolectando());


            telemetry.addData("=== PUERTA ===", "");
            telemetry.addData("Puerta Abierta:", recolector.isPuertaAbierta());

            telemetry.update();
        }
    }
}