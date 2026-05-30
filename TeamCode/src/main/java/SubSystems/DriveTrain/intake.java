package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class intake {
    private final DcMotor recolectorMotor;


    private String estado = "Detenido";
    private double puertaPosition = 0.5;


    private static final double RECOLECTOR_POWER = 0.7;
    private static final double RECOLECTOR_SPEED = 0.7;

    public intake(HardwareMap hardwareMap) {
        recolectorMotor = hardwareMap.get(DcMotor.class, "recolectorMotor");
        recolectorMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void init() {
        recolectorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        recolectorMotor.setPower(0.0);
    }


    public void activarRecolector() {
        recolectorMotor.setPower(RECOLECTOR_SPEED);
        estado = "Recolectando";
    }

    public void detenerRecolector() {
        recolectorMotor.setPower(0.0);
        estado = "Detenido";
    }

    public void normalRecolector() {
        recolectorMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        estado = "Recolector Normal";
    }

    public void invertirRecolector() {
        recolectorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        estado = "Recolector Invertido";
    }



    public double getRecolectorPower() {
        return recolectorMotor.getPower();
    }

    public String getEstado() {
        return estado;
    }

    public boolean isRecolectando() {
        return recolectorMotor.getPower() > 0.0;
    }

    public boolean isPuertaAbierta() {
        return puertaPosition > 0.5;
    }
}