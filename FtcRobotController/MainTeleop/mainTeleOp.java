package MainTeleop;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import SubSystems.DriveTrain.chassis_wheels;
import SubSystems.DriveTrain.shooter;
import SubSystems.DriveTrain.recolector;


@TeleOp(name = "D-Vake v1 - Main", group = "TeleOp")
public class mainTeleOp extends LinearOpMode {

    // Instancias de subsistemas
    private chassis_wheels drive;
    private shooter disparador;
    private recolector recolector;

    // Declaración de motores
    private DcMotor chassis1;
    private DcMotor chassis2;

    // Cámara
    //OpenCvCamera camera;

    // Constantes
    private static final double DEADBAND = 0.05;

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {

        // ==========================================
        // INICIALIZAR SUBSISTEMAS
        // ==========================================
        drive = new chassis_wheels(hardwareMap);
        disparador = new shooter(hardwareMap);
        recolector = new recolector(hardwareMap);
        boolean frenteInvertido = false;
        boolean ultimoEstadoY1 = false; // "Y1" porque es del Gamepad 1

        chassis1 = hardwareMap.get(DcMotor.class, "chassis1");
        chassis2 = hardwareMap.get(DcMotor.class, "chassis2");

        chassis1.setDirection(DcMotorSimple.Direction.FORWARD);
        chassis2.setDirection(DcMotorSimple.Direction.REVERSE);

        drive.init();
        disparador.init();
        recolector.init();


        // ==========================================
// INICIALIZAR CÁMARA


        waitForStart();
        recolector.activarRecolector();

        // ==========================================
        // BUCLE PRINCIPAL
        // ==========================================
        while (opModeIsActive()) {

            // ==========================================
            // LÓGICA DE INVERSIÓN DE FRENTE (TOGGLE Y)
            // ==========================================
            if (gamepad1.y && !ultimoEstadoY1) {
                frenteInvertido = !frenteInvertido; // Cambia entre true y false
            }
            ultimoEstadoY1 = gamepad1.y;

            // CONTROL CHASIS
            double avance = gamepad1.right_trigger - gamepad1.left_trigger;
            double giro = gamepad1.left_stick_x;

            avance = applyDeadband(avance);
            giro = applyDeadband(giro);

            double potenciaIzquierda = Math.max(-1.0, Math.min(1.0, avance + giro));
            double potenciaDerecha   = Math.max(-1.0, Math.min(1.0, avance - giro));

            drive.setPower(potenciaIzquierda, potenciaDerecha);

            //GAMEPADS

            if (gamepad2.b) {
                disparador.disparar();
            } else {
                disparador.detener();
            }


            if (gamepad2.left_bumper)
                recolector.activarRecolector();

            if (gamepad2.x) {
               recolector.detenerRecolector();
           }

            if (gamepad2.y) {
                recolector.normalRecolector();
            }
            else {
                recolector.invertirRecolector();
            }

            if (gamepad2.dpad_right) {
                recolector.abrirPuerta();
            }

            if (gamepad2.dpad_left) {
                recolector.cerrarPuerta();
            }

            // ==========================================
            // TELEMETRÍA
            // ==========================================
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
            telemetry.addData("Puerta Posición:", String.format("%.2f", recolector.getPuertaPosition()));
            telemetry.addData("Puerta Abierta:", recolector.isPuertaAbierta());


            telemetry.update();
        }
    }

    // Zona muerta
    private double applyDeadband(double value) {
        if (Math.abs(value) < DEADBAND) {
            return 0.0;
        }
        return value;
    }
}