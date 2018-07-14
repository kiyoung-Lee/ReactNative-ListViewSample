package spot_api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatternCheckerTest {

    private PatternChecker patternChecker;
    private List<String> patternList;
    private Calendar calendar;

    @Before
    public void setUp(){
        patternChecker = new PatternChecker();
        patternList = new ArrayList<>();
        calendar = Calendar.getInstance();
    }

    @Test   // 고정 시간 (수, 14:10 ~ 15:40)
    public void fixedDayTest1() throws Exception {
        String pattern = "3.14.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,13,5);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 고정 시간 (토, 14:10 ~ 15:40)
    public void fixedDayTest2() throws Exception {
        String pattern = "6.14.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,14,14,10);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test   // 고정 시간 (일, 14:10 ~ 15:40)
    public void fixedDayTest3() throws Exception {
        String pattern = "0.14.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,15,14,30);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test   // 고정 시간 (수, 14:10 ~ 16:40)
    public void fixedDayTest4() throws Exception {
        String pattern = "3.14.10.16.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,15,6);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test   // 고정 시간 (수, 14:10 ~ 16:40)
    public void fixedDayTest5() throws Exception {
        String pattern = "3.14.10.16.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,16,40);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 고정 시간 (수, 14:10 ~ 16:40)
    public void fixedDayTest6() throws Exception {
        String pattern = "3.14.10.16.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,16,45);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 고정 시간 (수, 15:10 ~ 15:40)
    public void fixedDayTest7() throws Exception {
        String pattern = "3.15.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,13,5);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 고정 시간 (수, 15:10 ~ 15:40)
    public void fixedDayTest8() throws Exception {
        String pattern = "3.15.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,15,10);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test   // 고정 시간 (수, 15:10 ~ 15:40)
    public void fixedDayTest9() throws Exception {
        String pattern = "3.15.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,15,15);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test   // 고정 시간 (수, 15:10 ~ 15:40)
    public void fixedDayTest10() throws Exception {
        String pattern = "3.15.10.15.40";
        patternList.add(pattern);

        Date testDate = getTestDate(2018,7,11,15,40);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 평일 전체 (15:10 ~ 15:40)
    public void allWeekdayTest() throws Exception {
        String pattern = "100.15.10.15.40";
        patternList.add(pattern);

        // 일,월,화,수,목,금,토
        Date SUN = getTestDate(2018,7,8,15,30);
        Date MON = getTestDate(2018,7,9,15,30);
        Date TUE = getTestDate(2018,7,10,15,30);
        Date WED = getTestDate(2018,7,11,15,30);
        Date THR = getTestDate(2018,7,12,15,30);
        Date FRI = getTestDate(2018,7,13,15,30);
        Date SAT = getTestDate(2018,7,14,15,30);

        Boolean result0 = patternChecker.IsAvailableTime(patternList, SUN);
        Boolean result1 = patternChecker.IsAvailableTime(patternList, MON);
        Boolean result2 = patternChecker.IsAvailableTime(patternList, TUE);
        Boolean result3 = patternChecker.IsAvailableTime(patternList, WED);
        Boolean result4 = patternChecker.IsAvailableTime(patternList, THR);
        Boolean result5 = patternChecker.IsAvailableTime(patternList, FRI);
        Boolean result6 = patternChecker.IsAvailableTime(patternList, SAT);

        Assert.assertFalse(result0);
        Assert.assertTrue(result1);
        Assert.assertTrue(result2);
        Assert.assertTrue(result3);
        Assert.assertTrue(result4);
        Assert.assertTrue(result5);
        Assert.assertFalse(result6);
    }

    @Test   // 주말 전체 (15:10 ~ 15:40)
    public void allWeekendTest() throws Exception {
        String pattern = "101.15.10.15.40";
        patternList.add(pattern);

        // 일,월,화,수,목,금,토
        Date SUN = getTestDate(2018,7,8,15,30);
        Date MON = getTestDate(2018,7,9,15,30);
        Date TUE = getTestDate(2018,7,10,15,30);
        Date WED = getTestDate(2018,7,11,15,30);
        Date THR = getTestDate(2018,7,12,15,30);
        Date FRI = getTestDate(2018,7,13,15,30);
        Date SAT = getTestDate(2018,7,14,15,30);

        Boolean result0 = patternChecker.IsAvailableTime(patternList, SUN);
        Boolean result1 = patternChecker.IsAvailableTime(patternList, MON);
        Boolean result2 = patternChecker.IsAvailableTime(patternList, TUE);
        Boolean result3 = patternChecker.IsAvailableTime(patternList, WED);
        Boolean result4 = patternChecker.IsAvailableTime(patternList, THR);
        Boolean result5 = patternChecker.IsAvailableTime(patternList, FRI);
        Boolean result6 = patternChecker.IsAvailableTime(patternList, SAT);

        Assert.assertTrue(result0);
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(result3);
        Assert.assertFalse(result4);
        Assert.assertFalse(result5);
        Assert.assertTrue(result6);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest1() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,11,0);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest2() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,11,10);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest3() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,12,10);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest4() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,13,40);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest5() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,14,40);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest6() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,15,20);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest7() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,18,40);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertTrue(result);
    }

    @Test // 고정 시간 연속 (수, 11:10 ~ 13:40 , 15:20 ~ 20:10)
    public void fixDayTwoTimeTest8() throws Exception {
        String pattern1 = "3.11.10.13.40";
        String pattern2 = "3.15.20.20.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        Date testDate = getTestDate(2018,7,11,20,10);

        Boolean result = patternChecker.IsAvailableTime(patternList, testDate);
        Assert.assertFalse(result);
    }

    @Test   // 평일 전체 연속 (평일, 10:10 ~ 15:40, 17:15 ~ 21:30)
    public void allWeekdayTwoTimeTest() throws Exception {
        String pattern1 = "100.10.10.15.40";
        String pattern2 = "100.17.15.21.30";
        patternList.add(pattern1);
        patternList.add(pattern2);

        // 일,월,화,수,목,금,토
        Date SUN = getTestDate(2018,7,8,15,30);
        Date MON = getTestDate(2018,7,9,10,10);
        Date TUE = getTestDate(2018,7,10,13,30);
        Date WED = getTestDate(2018,7,11,15,30);
        Date THR = getTestDate(2018,7,12,17,15);
        Date FRI = getTestDate(2018,7,13,20,30);
        Date SAT = getTestDate(2018,7,14,15,30);

        Boolean result0 = patternChecker.IsAvailableTime(patternList, SUN);
        Boolean result1 = patternChecker.IsAvailableTime(patternList, MON);
        Boolean result2 = patternChecker.IsAvailableTime(patternList, TUE);
        Boolean result3 = patternChecker.IsAvailableTime(patternList, WED);
        Boolean result4 = patternChecker.IsAvailableTime(patternList, THR);
        Boolean result5 = patternChecker.IsAvailableTime(patternList, FRI);
        Boolean result6 = patternChecker.IsAvailableTime(patternList, SAT);

        Assert.assertFalse(result0);
        Assert.assertTrue(result1);
        Assert.assertTrue(result2);
        Assert.assertTrue(result3);
        Assert.assertTrue(result4);
        Assert.assertTrue(result5);
        Assert.assertFalse(result6);
    }

    @Test   // 주말 전체 연속 (주말, 10:10 ~ 15:40 , 17:15 ~ 22:10)
    public void allWeekendTwoTimeTest() throws Exception {
        String pattern1 = "101.10.10.15.40";
        String pattern2 = "101.17.15.22.10";
        patternList.add(pattern1);
        patternList.add(pattern2);

        // 일,월,화,수,목,금,토
        Date SUN = getTestDate(2018,7,8,11,30);
        Date MON = getTestDate(2018,7,9,15,30);
        Date TUE = getTestDate(2018,7,10,15,30);
        Date WED = getTestDate(2018,7,11,15,30);
        Date THR = getTestDate(2018,7,12,15,30);
        Date FRI = getTestDate(2018,7,13,15,30);
        Date SAT = getTestDate(2018,7,14,17,15);

        Boolean result0 = patternChecker.IsAvailableTime(patternList, SUN);
        Boolean result1 = patternChecker.IsAvailableTime(patternList, MON);
        Boolean result2 = patternChecker.IsAvailableTime(patternList, TUE);
        Boolean result3 = patternChecker.IsAvailableTime(patternList, WED);
        Boolean result4 = patternChecker.IsAvailableTime(patternList, THR);
        Boolean result5 = patternChecker.IsAvailableTime(patternList, FRI);
        Boolean result6 = patternChecker.IsAvailableTime(patternList, SAT);

        Assert.assertTrue(result0);
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(result3);
        Assert.assertFalse(result4);
        Assert.assertFalse(result5);
        Assert.assertTrue(result6);
    }

    private Date getTestDate(int year, int month, int date, int hour, int minutes){
        int realMonth = month -1;
        calendar.set(year, realMonth, date, hour, minutes);
        Date testDate = calendar.getTime();

        return testDate;
    }
}