package spot_api;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PatternFactoryTest {

    @Test
    public void patternCreateTest() throws Exception {
        String inputString1 = "1.12.24.16.0";
        String inputString2 = "3.16.12.21.22";

        List<String> inputList = new ArrayList<>();
        inputList.add(inputString1);
        inputList.add(inputString2);

        List<Pattern> patternList = PatternFactory.create(inputList);

        Assert.assertNotNull(patternList);
        Assert.assertEquals(2, patternList.size());

        //Assert Pattern 1
        Pattern pattern1 = patternList.get(0);
        Assert.assertEquals(1, pattern1.getDay());
        Assert.assertEquals(12, pattern1.getStartHour());
        Assert.assertEquals(24, pattern1.getStartMinutes());
        Assert.assertEquals(16, pattern1.getEndHour());
        Assert.assertEquals(0, pattern1.getEndMinutes());

        //Assert Pattern2
        Pattern pattern2 = patternList.get(1);
        Assert.assertEquals(3, pattern2.getDay());
        Assert.assertEquals(16, pattern2.getStartHour());
        Assert.assertEquals(12, pattern2.getStartMinutes());
        Assert.assertEquals(21, pattern2.getEndHour());
        Assert.assertEquals(22, pattern2.getEndMinutes());
    }

}