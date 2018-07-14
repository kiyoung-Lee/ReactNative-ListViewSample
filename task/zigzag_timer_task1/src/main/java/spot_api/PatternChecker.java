package spot_api;

import java.util.Date;
import java.util.List;

public class PatternChecker {

    // day rule = 0~6 - (일~토) / 100,101 - (평일,주말)
    // hour rule = 0~23
    // minute rule = 0~59
    // pattern string rule = (day. startHour, startMinutes, endHour, endMinutes)
    // ex) "2.10.0.14.20" -> 화요일 10:00 ~ 14:20
    public boolean IsAvailableTime(List<String> inputPattern, Date time){
        Date validateTime = TimeValidator.validate(time);
        List<Pattern> patternList = PatternFactory.create(inputPattern);
        int checkDay = validateTime.getDay();

        boolean isAvailable = false;
        for(Pattern pattern: patternList) {
            int patternDay = pattern.getDay();

            if (isAvailable == false) {
                boolean condition;

                if (patternDay == 100) {
                    condition = 1 <= checkDay && checkDay <= 5;
                    isAvailable = getAvailableByCondition(condition, validateTime, pattern);
                } else if(patternDay == 101){
                    condition = checkDay == 0 || checkDay == 6;
                    isAvailable = getAvailableByCondition(condition, validateTime, pattern);
                } else {
                    condition = patternDay == checkDay;
                    isAvailable = getAvailableByCondition(condition, validateTime, pattern);
                }
            }
        }

        return isAvailable;
    }

    private Boolean getAvailableByCondition(Boolean condition, Date validateTime, Pattern pattern){
        boolean isAvailable = false;

        if (condition) {
            isAvailable = checkTimePattern(validateTime, pattern);
        } else {
            isAvailable = false;
        }

        return isAvailable;
    }

    private boolean checkTimePattern(Date validateTime, Pattern pattern){
        boolean isAvailable = false;
        int checkHour = validateTime.getHours();
        int checkMinutes = validateTime.getMinutes();

        int patternStartHour = pattern.getStartHour();
        int patternStartMinutes = pattern.getStartMinutes();
        int patternEndHour = pattern.getEndHour();
        int patternEndMinutes = pattern.getEndMinutes();

        if(patternStartHour == patternEndHour){
            if(checkHour == patternStartHour) {
                if (patternStartMinutes <= checkMinutes && checkMinutes < patternEndMinutes) {
                    isAvailable = true;
                } else {
                    isAvailable = false;
                }
            }else{
                isAvailable = false;
            }
        }else{
            if(patternStartHour <= checkHour && checkHour <= patternEndHour){
                if(patternStartHour == checkHour){
                    if(patternStartMinutes <= checkMinutes){
                        isAvailable = true;
                    }else{
                        isAvailable = false;
                    }
                } else if(patternEndHour == checkHour){
                    if(checkMinutes < patternEndMinutes){
                        isAvailable = true;
                    }else{
                        isAvailable = false;
                    }
                }else{
                    isAvailable = true;
                }
            }else{
                isAvailable = false;
            }
        }

        return isAvailable;
    }
}
