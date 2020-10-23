package trickytasks.DateWorker;


public class DateWorkerImpl implements DateWorker {
    @Override
    public boolean isLeapYear(int year) {
        if (year < 0) {
            throw new IllegalStateException();
        } else {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    return year % 400 == 0;
                } else return true;
            } else return false;
        }
    }

    @Override
    public boolean isValidDate(int year, int month, int day) {
        if (year < 0) {
            return false;
        } else if (month < 1 && month > 12) {
            return false;
        } else if (day >= 1 && day <= 31) {
            if (isLeapYear(year) && month == 2) {
                return day <= 29;
            } else {
                return day <= 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        }
        return true;
    }

    @Override
    public int getDayOfWeek(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            if (month < 3) {
                --year;
                month += 10;
            } else
                month -= 2;
            return ((day + 31 * month / 12 + year + year / 4 - year / 100 + year / 400 - 1) % 7);
        } else
            throw new IllegalArgumentException();
    }

    @Override
    public String toString(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            String date = ("Day " + day + " Month " + month + " Year " + year);
            return date;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int countDays(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            int countDays = 0;
            long today = System.currentTimeMillis();
            int monthLength[] = {
                    31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
            };
            today = (int) Math.floor((3 + today / 1000. / 60. / 60.) / 24);
            System.out.println(today);
            for (int i = 0; i < Math.abs(1970 - year); i++) {
                if (this.isLeapYear(1970 + i))
                    countDays += 366;
                else
                    countDays += 365;
            }
            for (int i = 0; i <= month - 1; i++) {
                if (this.isLeapYear(year) && i == 2)
                    countDays += 1;
                else countDays += monthLength[i];
            }
            countDays += day - 1;
            System.out.println(countDays);
            return (int) Math.abs(today - countDays);

        } else {
            throw new IllegalArgumentException();
        }
    }
}
