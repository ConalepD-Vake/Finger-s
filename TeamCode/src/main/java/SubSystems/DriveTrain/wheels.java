package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class wheels {


    private final HardwareMap hardwareMap;

    public wheels(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {

        DcMotorEx chassis1 = hardwareMap.get(DcMotorEx.class, "chassis1");
        DcMotorEx chassis2 = hardwareMap.get(DcMotorEx.class, "chassis2");

        chassis1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        chassis1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        chassis1.setDirection(DcMotorEx.Direction.FORWARD);

        chassis2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chassis2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        chassis2.setDirection(DcMotorEx.Direction.REVERSE);
    }
}

