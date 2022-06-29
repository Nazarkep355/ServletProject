package Utility;

import com.example.finalproject3.Adapter.TrainAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TrainAdapterTest {
    @Test
    void getDateFromFormTest(){
        Date actual = TrainAdapter.getDateFromForm("2022-06-16T16:30");
        Date expected = new Date(actual.getTime());
        expected.setYear(2022);
        expected.setMonth(05);
        expected.setDate(16);
        expected.setHours(16);
        expected.setMinutes(30);
        expected.setSeconds(0);
        Assertions.assertEquals(expected,actual);
        actual = TrainAdapter.getDateFromForm("16-06-2022");
        expected = new Date(actual.getTime());
        expected.setHours(0);
        expected.setMinutes(0);
        Assertions.assertEquals(expected,actual);
    }
}
