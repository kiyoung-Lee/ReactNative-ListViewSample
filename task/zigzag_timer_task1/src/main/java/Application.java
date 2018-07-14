import spot_api.PatternChecker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args){
        System.out.println("///////////   Pattern Checker!!    ///////////");
        System.out.println("///  day rule = 0~6 - (일~토) / 100,101 - (평일,주말)");
        System.out.println("///  hour rule = 0~23");
        System.out.println("///  minute rule = 0~59");
        System.out.println("///  pattern string rule = (day. startHour, startMinutes, endHour, endMinutes)");
        System.out.println("///  ex) \"2.10.0.14.20\" -> 화요일 10:00 ~ 14:20");
        System.out.println("");

        sampleRun();
    }

    // Sample Run
    // Example Pattern (월, 11:20 ~ 15:30)
    // Test Time : 2018.7.9(월) 11:30
    private static void sampleRun(){
        String pattern = "1.11.20.15.30";
        List<String> patternList = new ArrayList<>();
        patternList.add(pattern);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,6,9,11,30);
        Date testDate = calendar.getTime();

        PatternChecker patternChecker = new PatternChecker();
        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);

        if(result){
            System.out.println("호출 가능 시간입니다.");
        }else{
            System.out.println("현재 시간은 호출 불가합니다.");
        }
    }
}
