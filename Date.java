package ca.bcit.comp2522.bank;

/**
 * Represents a date with year, month, and day.
 * Provides methods for date validation and day-of-week calculation.
 *
 * @author Ziad Malik
 * @version 1.0
 */
public class Date {
    private final int year;
    private final int month;
    private final int day;

    private static final int MIN_YEAR = 1800;
    private static final int MAX_YEAR = 2026;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private static final int MIN_DAY = 1;
    private static final int DAYS_IN_LONG_MONTH = 31;
    private static final int DAYS_IN_SHORT_MONTH = 30;
    private static final int DAYS_IN_FEB_LEAP = 29;
    private static final int DAYS_IN_FEB_NORMAL = 28;

    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;
    private static final int MARCH = 3;
    private static final int APRIL = 4;
    private static final int MAY = 5;
    private static final int JUNE = 6;
    private static final int JULY = 7;
    private static final int AUGUST = 8;
    private static final int SEPTEMBER = 9;
    private static final int OCTOBER = 10;
    private static final int NOVEMBER = 11;
    private static final int DECEMBER = 12;

    private static final int SATURDAY = 0;
    private static final int SUNDAY = 1;
    private static final int MONDAY = 2;
    private static final int TUESDAY = 3;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 5;
    private static final int FRIDAY = 6;

    private static final int LEAP_YEAR_DIVISOR = 4;
    private static final int CENTURY_YEAR_DIVISOR = 100;
    private static final int QUAD_CENTURY_DIVISOR = 400;

    private static final int TWELVE_DIVISOR = 12;
    private static final int FOUR_DIVISOR = 4;
    private static final int SEVEN_DIVISOR = 7;
    private static final int CENTURY_1800_OFFSET = 2;
    private static final int CENTURY_2000_OFFSET = 6;
    private static final int LEAP_YEAR_JAN_FEB_OFFSET = 6;

    /**
     * Constructs a Date object with the specified year, month, and day.
     *
     * @param year the year (must be between 1800 and 2026)
     * @param month the month (must be between 1 and 12)
     * @param day the day (must be valid for the given month and year)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Date(final int year, final int month, final int day) {
        validateDate(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Gets the day of the month.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the date in YYYY-MM-DD format.
     *
     * @return the date string in format YYYY-MM-DD
     */
    public String getYyyyMmDd() {
        final String yearStr;
        final String monthStr;
        final String dayStr;

        yearStr = String.valueOf(year);
        monthStr = month < 10 ? "0" + month : String.valueOf(month);
        dayStr = day < 10 ? "0" + day : String.valueOf(day);

        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * Gets the name of the month for this date.
     *
     * @return the month name (e.g., "January", "February")
     */
    public String getMonthName() {
        return getMonthName(month);
    }

    /**
     * Calculates and returns the day of the week for this date.
     * Uses a specific algorithm for dates between 1800-2026:

     * Step 1: Calculate the number of twelves in the last two digits of the year
     * Step 2: Calculate the remainder after removing those twelves
     * Step 3: Calculate the number of fours in step 2
     * Step 4: Add the day of the month
     * Step 5: Add the month code (jan-dec: 144025036146)
     * Step 6: Sum all previous numbers and mod by 7
     * Step 7: Map result to day name (0=Sat, 1=Sun, 2=Mon, 3=Tue, 4=Wed, 5=Thu, 6=Fri)

     * Special adjustments:
     * - Add 6 for dates in the 2000s
     * - Add 2 for dates in the 1800s
     * - Add 6 for January/February dates in leap years
     *
     * @return the day of the week as a lowercase string
     */
    public String getDayOfTheWeek() {
        final int lastTwoDigits;
        final int centuryOffset;
        final int leapYearOffset;
        final int step1;
        final int step2;
        final int step3;
        final int step4;
        final int step5;
        final int step6;
        final int dayCode;

        lastTwoDigits = year % CENTURY_YEAR_DIVISOR;

        // Determine century offset
        if (year >= MIN_YEAR && year < 1900) {
            centuryOffset = CENTURY_1800_OFFSET;
        } else if (year >= 2000 && year <= MAX_YEAR) {
            centuryOffset = CENTURY_2000_OFFSET;
        } else {
            centuryOffset = 0;
        }

        // Determine leap year offset for January and February
        if (isLeapYear(year) && (month == JANUARY || month == FEBRUARY)) {
            leapYearOffset = LEAP_YEAR_JAN_FEB_OFFSET;
        } else {
            leapYearOffset = 0;
        }

        // Step 1: Calculate the number of twelves in last two digits
        step1 = lastTwoDigits / TWELVE_DIVISOR;

        // Step 2: Calculate remainder
        step2 = lastTwoDigits % TWELVE_DIVISOR;

        // Step 3: Calculate the number of fours in step 2
        step3 = step2 / FOUR_DIVISOR;

        // Step 4: The day of the month
        step4 = day;

        // Step 5: Add month code
        step5 = getMonthCode(month);

        // Step 6: Sum all numbers and mod by 7
        step6 = centuryOffset + leapYearOffset + step1 + step2 + step3 + step4 + step5;
        dayCode = step6 % SEVEN_DIVISOR;

        return getDayName(dayCode);
    }

    /**
     * Validates the year, month, and day parameters.
     *
     * @param year the year to validate
     * @param month the month to validate
     * @param day the day to validate
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private static void validateDate(final int year, final int month, final int day) {
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new IllegalArgumentException("Year must be between 1800 and 2026");
        }

        if (month < MIN_MONTH || month > MAX_MONTH) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        if (day < MIN_DAY) {
            throw new IllegalArgumentException("Day must be at least 1");
        }

        final int maxDaysInMonth;
        maxDaysInMonth = getMaxDaysInMonth(month, year);

        if (day > maxDaysInMonth) {
            throw new IllegalArgumentException("Day must be valid for the given month and year");
        }
    }

    /**
     * Gets the maximum number of days in a given month for a given year.
     *
     * @param month the month
     * @param year the year
     * @return the maximum number of days in the month
     */
    private static int getMaxDaysInMonth(final int month, final int year) {
        final int maxDays;

        if (month == FEBRUARY) {
            if (isLeapYear(year)) {
                maxDays = DAYS_IN_FEB_LEAP;
            } else {
                maxDays = DAYS_IN_FEB_NORMAL;
            }
        } else if (month == APRIL || month == JUNE || month == SEPTEMBER || month == NOVEMBER) {
            maxDays = DAYS_IN_SHORT_MONTH;
        } else {
            maxDays = DAYS_IN_LONG_MONTH;
        }

        return maxDays;
    }

    /**
     * Determines if a year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    private static boolean isLeapYear(final int year) {
        final boolean isLeap;

        if (year % QUAD_CENTURY_DIVISOR == 0) {
            isLeap = true;
        } else if (year % CENTURY_YEAR_DIVISOR == 0) {
            isLeap = false;
        } else if (year % LEAP_YEAR_DIVISOR == 0) {
            isLeap = true;
        } else {
            isLeap = false;
        }

        return isLeap;
    }

    /**
     * Gets the month name for a given month number.
     * This is a helper method used by the public getMonthName() instance method.
     *
     * @param month the month number (1-12)
     * @return the month name (e.g., "January", "February")
     */
    private static String getMonthName(final int month) {

        return switch (month) {
            case JANUARY -> "January";
            case FEBRUARY -> "February";
            case MARCH -> "March";
            case APRIL -> "April";
            case MAY -> "May";
            case JUNE -> "June";
            case JULY -> "July";
            case AUGUST -> "August";
            case SEPTEMBER -> "September";
            case OCTOBER -> "October";
            case NOVEMBER -> "November";
            case DECEMBER -> "December";
            default -> "Unknown";
        };
    }

    /**
     * Gets the month code used in day-of-week calculation.
     * Month codes for jan-dec: 1,4,4,0,2,5,0,3,6,1,4,6
     *
     * @param month the month
     * @return the month code
     */
    private static int getMonthCode(final int month) {

        return switch (month) {
            case JANUARY, OCTOBER -> 1;
            case FEBRUARY, MARCH, NOVEMBER -> 4;
            case APRIL -> 0;
            case MAY -> 2;
            case JUNE -> 5;
            case JULY -> 0;
            case AUGUST -> 3;
            case SEPTEMBER, DECEMBER -> 6;
            default -> 0;
        };
    }

    /**
     * Converts a day code to a day name.
     *
     * @param dayCode the day code (0-6)
     * @return the day name in lowercase
     */
    private static String getDayName(final int dayCode) {

        return switch (dayCode) {
            case SATURDAY -> "saturday";
            case SUNDAY -> "sunday";
            case MONDAY -> "monday";
            case TUESDAY -> "tuesday";
            case WEDNESDAY -> "wednesday";
            case THURSDAY -> "thursday";
            case FRIDAY -> "friday";
            default -> "unknown";
        };
    }
}