package ua.com.alevel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestHelper {

    public static final String TEST = "test";
    public static Date TEST_DATE = null;
    static {
        try {
            TEST_DATE = new SimpleDateFormat( "yyyy.MM.dd" ).parse( "2022.05.20" );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //public static final String EMAIL = "email";
}
