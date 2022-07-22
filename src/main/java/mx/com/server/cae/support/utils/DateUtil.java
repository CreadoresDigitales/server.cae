package mx.com.server.cae.support.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

@Component
public class DateUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Locale LOCALE_MX = new Locale("es", "MX");

    public static final TimeZone TIMEZONE_MX = TimeZone.getTimeZone("America/Mexico_City");

    public static Calendar getCurrentCalendar() {
        return GregorianCalendar.getInstance(TIMEZONE_MX, LOCALE_MX);
    }

    public Date getCurrentDateLocaleMX() {
        return getCurrentCalendar().getTime();
    }

}
