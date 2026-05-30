package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


@SuppressWarnings("ALL")
public class shooter {

    private final DcMotorEx shooterMotor;
    private String estado = "Detenido";

    public shooter(HardwareMap hardwareMap) {

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorEx.Direction.REVERSE);

    }

    public void init() {

        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void disparar() {
        shooterMotor.setPower(1.0);
        estado = "Disparando";



    }

    public void disparar(double potencia) {
        shooterMotor.setPower(potencia);
        estado = "Disparando";
    }

    public void detener() {
        shooterMotor.setPower(0.0);
        estado = "Detenido";
    }

    public void antiAtasco() {
        shooterMotor.setPower(-1);
        estado = "Desatascando";

        try {
            Thread.sleep(500); // 500 ms en reversa
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        shooterMotor.setPower(0);
    }


    public String getEstado() {
        return estado;
    }

    public double getMotorPower() {
        return shooterMotor.getPower();
    }

}