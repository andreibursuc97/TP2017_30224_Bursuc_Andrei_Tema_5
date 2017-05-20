import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andrei on 14/05/2017.
 */
public class MyDate extends Date {

    @Override
    public boolean equals(Object obj) {

        Date date=(Date)obj;
        Calendar calendar=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();
        calendar.setTime(date);
        calendar2.setTime(this);
        int month1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        int month2=calendar2.get(Calendar.MONTH);
        int day2=calendar2.get(Calendar.DAY_OF_MONTH);
        if(month1!=month2)
            return false;
        if(day1!=day2)
            return false;
        return true;

    }

    @Override
    public int hashCode()
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(this);
        int month1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        return month1+day1;
    }
}
