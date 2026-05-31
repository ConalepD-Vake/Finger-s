package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class intake {
    private final DcMotor intakerMotor;


    private String estado = "stopped";

    private static final double intaker_SPEED = 0.7;

    public intake(HardwareMap hardwareMap) {
        intakerMotor = hardwareMap.get(DcMotor.class, "intakerMotor");
        intakerMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void init() {
        intakerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakerMotor.setPower(0.0);
    }


    public void activateIntaker() {
        intakerMotor.setPower(intaker_SPEED);
        estado = "collecting";
    }

    public void stopIntake() {
        intakerMotor.setPower(0.0);
        estado = "stopped";
    }

    public void normalIntake() {
        intakerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        estado = "Normal Intake";
    }

    public void invertIntake() {
        intakerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        estado = "reversed Intake";
    }



    public double intakePower() {
        return intakerMotor.getPower();
    }

    public String getEstado() {
        return estado;
    }

    public boolean taking() {
        return intakerMotor.getPower() > 0.0;
    }
}