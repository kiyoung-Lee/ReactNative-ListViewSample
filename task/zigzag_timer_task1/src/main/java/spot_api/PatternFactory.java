package spot_api;

import java.util.ArrayList;
import java.util.List;

public class PatternFactory {

    public static List<Pattern> create(List<String> inputList){
        List<Pattern> patternList = new ArrayList<>();

        for(String input: inputList){
            String[] splitPattern = input.split("\\.");

            int day = Integer.valueOf(splitPattern[0]);

            int startHour = Integer.valueOf(splitPattern[1]);
            int startMinutes = Integer.valueOf(splitPattern[2]);

            int endHour = Integer.valueOf(splitPattern[3]);
            int endMinutes = Integer.valueOf(splitPattern[4]);

            Pattern pattern = new Pattern(day, startHour, startMinutes, endHour, endMinutes);
            patternList.add(pattern);
        }

        return patternList;
    }
}
