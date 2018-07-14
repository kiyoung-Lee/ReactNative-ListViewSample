package spot_api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TimeValidatorTest {

    @Test
    public void timeNullTest() throws Exception {
        Date now = new Date();
        Date result = TimeValidator.validate(null);

        Assert.assertEquals(now.toString(), result.toString());
    }

    @Test
    public void timeNotNullTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,3,12,12,50);

        Date inputDate = calendar.getTime();
        Date result = TimeValidator.validate(inputDate);

        Assert.assertEquals(result, inputDate);
    }
}