package SubSystems.DriveTrain;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class shooter {

    private final DcMotorEx shooterMotor;
    private String estado = "Stopped";

    public shooter(HardwareMap hardwareMap) {

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorEx.Direction.REVERSE);

    }

    public void init() {

        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void shoot() {
        shooterMotor.setPower(1.0);
        estado = "shooting";



    }
    public void stopShooter() {
        shooterMotor.setPower(0.0);
        estado = "stopped";
    }

    public void antijam() {
        shooterMotor.setPower(-1);
        estado = "unclogging";

        try {
            sleep(500);
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