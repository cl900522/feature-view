package acme.me.serialize.json;

import org.codehaus.jackson.annotate.JsonCreator;

public enum WeekDay {
    SUN(1), MON(2), TUE(3), WED(4), THI(5), FRI(6), STA(7);
    private int dayIndex;

    WeekDay(int day) {
        this.dayIndex = day;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    @JsonCreator
    public static WeekDay getDay(int dayIndex) {
        WeekDay[] days = WeekDay.values();
        for (WeekDay day : days) {
            if (day.dayIndex == dayIndex) {
                return day;
            }
        }
        return MON;
    }
}
