package spot_api;

public class Pattern {

    private int day;
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;

    public Pattern(int day, int startHour, int startMinutes, int endHour, int endMinutes) {
        this.day = day;
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.endHour = endHour;
        this.endMinutes = endMinutes;
    }

    public int getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }
}
