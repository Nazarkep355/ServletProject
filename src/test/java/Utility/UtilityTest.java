package Utility;

import com.example.finalproject3.Utility.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilityTest {
    @Test
    void timeFromSecondsTest(){
        Assertions.assertEquals("1:00", Utility.timeFromSeconds(3600));
        Assertions.assertEquals("1:26",Utility.timeFromSeconds(5160));
    }
}
