package spot_api;

import java.util.Date;

public class TimeValidator {

    public static Date validate(Date time){
        if(time == null){
            return new Date();
        }else{
            return time;
        }
    }
}
