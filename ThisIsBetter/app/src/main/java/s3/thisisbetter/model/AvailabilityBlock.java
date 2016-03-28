package s3.thisisbetter.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by steve on 3/25/16.
 */
public class AvailabilityBlock {

    private int availableCount;
    private int totalInvitedCount;
    private String weekday;
    private int day;
    private String month;
    private String timeRange;

    private Set<String> userIDs;

    public AvailabilityBlock(int totalInvitedCount, Calendar calendar, String month, String time, Set<String> userIDs) {
        this.availableCount = userIDs.size();
        this.totalInvitedCount = totalInvitedCount;
        this.weekday = this.convertToWeekdayString(calendar);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = month;
        this.timeRange = this.convertToTimeRange(time);

        this.userIDs = userIDs;

    }

    public String getWeekday() {
        return weekday;
    }

    public String getMonthDay() {
        return month + " " + day;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public int getTotalInvitedCount() {
        return totalInvitedCount;
    }

    private String convertToWeekdayString(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            case Calendar.SUNDAY:
                return "Sunday";
            default:
                return "Error";
        }
    }

    private String convertToTimeRange(String time) {
        switch(time) {
            case "12:00am":
                return "12:00am-1:00am";
            case "1:00am":
                return "1:00am-2:00am";
            case "2:00am":
                return "2:00am-3:00am";
            case "3:00am":
                return "3:00am-4:00am";
            case "4:00am":
                return "4:00am-5:00am";
            case "5:00am":
                return "5:00am-6:00am";
            case "6:00am":
                return "6:00am-7:00am";
            case "7:00am":
                return "7:00am-8:00am";
            case "8:00am":
                return "8:00am-9:00am";
            case "9:00am":
                return "9:00am-10:00am";
            case "10:00am":
                return "10:00am-11:00am";
            case "11:00am":
                return "11:00am-12:00pm";
            case "12:00pm":
                return "12:00pm-1:00pm";
            case "1:00pm":
                return "1:00pm-2:00pm";
            case "2:00pm":
                return "2:00pm-3:00pm";
            case "3:00pm":
                return "3:00pm-4:00pm";
            case "4:00pm":
                return "4:00pm-5:00pm";
            case "5:00pm":
                return "5:00pm-6:00pm";
            case "6:00pm":
                return "6:00pm-7:00pm";
            case "7:00pm":
                return "7:00pm-8:00pm";
            case "8:00pm":
                return "8:00pm-9:00pm";
            case "9:00pm":
                return "9:00pm-10:00pm";
            case "10:00pm":
                return "10:00pm-11:00pm";
            case "11:00pm":
                return "11:00pm-12:00am";
            default:
                return "error";
        }
    }

}