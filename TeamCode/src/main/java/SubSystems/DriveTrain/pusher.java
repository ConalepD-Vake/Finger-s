package SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class pusher {

    CRServo pusher;

    double velocidadEmpuje = 1.0;

    boolean pushing = false;

    ElapsedTime timer = new ElapsedTime();

    public pusher(HardwareMap hardwareMap){

        pusher = hardwareMap.get(CRServo.class, "pusher");

        pusher.setPower(0);
    }

    public void activate(){

        pusher.setPower(velocidadEmpuje);

        timer.reset();

        pushing = true;
    }

    public void reverse(){

        pusher.setPower(-velocidadEmpuje);

        timer.reset();

        pushing = true;
    }
    public void update(){

        if(pushing && timer.milliseconds() > 250){

            pusher.setPower(0);

            pushing = false;
        }
    }

    public void stop(){

        pusher.setPower(0);

        pushing = false;
    }
}