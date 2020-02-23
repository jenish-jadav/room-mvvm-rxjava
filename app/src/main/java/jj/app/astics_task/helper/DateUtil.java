package jj.app.astics_task.helper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    @Nullable
    public static String getDate(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Date getDate(String date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getDate(String date, String requestFormat, String responseFormat) {
        try {
            Date tempDate = getDate(date, requestFormat);
            return getDate(tempDate, responseFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getCurrentDate(String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.format(getCurrentDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Date getCurrentDateTime() {
        try {
            Calendar cal = Calendar.getInstance();
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getDate(int selectedYear, int selectedMonth, int selectedDay) {
        try {
            return String.format(Locale.getDefault(), "%02d", selectedDay) + "/" + String.format(Locale.getDefault(), "%02d", selectedMonth) + "/" + String.format(Locale.getDefault(), "%04d", selectedYear);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static long getMinuteDifference(Date start_date, Date end_date) {
        long diff = 0;
        try {
            long duration = end_date.getTime() - start_date.getTime();
            diff = TimeUnit.MILLISECONDS.toMinutes(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @return file name eg.yyyyMMdd_HHmmss
     */
    @Nullable
    public static String getFileName() {
        try {
            return getDate(Calendar.getInstance().getTime(), "yyyyMMdd_HHmmss");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class Format {
        public final static String DATABASE = "yyyy-MM-dd HH:mm:ss";

        public final static String DISPLAY_DATETIME = "dd/MM/yyyy HH:mm:ss";
        public final static String DISPLAY_DATETIME_AM_PM = "dd/MM/yyyy HH:mm tt";

        public final static String DISPLAY_DATE = "dd/MM/yyyy";

        public final static String DISPLAY_TIME = "HH:mm";
        public final static String DISPLAY_TIME_AM_PM = "HH:mm tt";
    }

}
