package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("ALL")
public class wheels {




    private DcMotorEx chassis1;
    private DcMotorEx chassis2;
    private final HardwareMap hardwareMap;

    public wheels(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {

        chassis1 = hardwareMap.get(DcMotorEx.class, "chassis1");
        chassis2 = hardwareMap.get(DcMotorEx.class, "chassis2");

        chassis1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        chassis1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        chassis1.setDirection(DcMotorEx.Direction.FORWARD);

        chassis2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassis2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        chassis2.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public void setPower(double power) {
        chassis1.setPower(power);
        chassis2.setPower(power);
    }

    public void setPower(double power1, double power2) {
        chassis1.setPower(power1);
        chassis2.setPower(power2);
    }

    public void stop() {
        chassis1.setPower(0.0);
        chassis2.setPower(0.0);
    }

    public double getPower1() {
        return chassis1.getPower();
    }

    public double getPower2() {
        return chassis2.getPower();
    }

    public int getLeftEncoder() {

        return chassis1.getCurrentPosition();
    }

    public int getRightEncoder() {

        return chassis2.getCurrentPosition();
    }

    public void setDrivePower(double forward, double turn) {

        double turnSensitivity = 1.5;

        double leftPower = forward + (turn * turnSensitivity);
        double rightPower = forward - (turn * turnSensitivity);

        chassis1.setPower(Math.max(-1.0, Math.min(1.0, leftPower)));
        chassis2.setPower(Math.max(-1.0, Math.min(1.0, rightPower)));
    }
    public void resetEncoders() {

        chassis1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        chassis2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        chassis1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        chassis2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

