package trickytasks.DateWorker;

public interface DateWorker {
    boolean isLeapYear(int year);

    boolean isValidDate(int year, int month, int day);

    int getDayOfWeek(int year, int month, int day);

    String toString(int year, int month, int day);

    int countDays(int year, int month, int day);

}
