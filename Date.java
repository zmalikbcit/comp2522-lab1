package ca.bcit.comp2522.bank;

/**
 * Represents a date with year, month, and day.
 * Provides methods for date validation and day-of-week calculation.
 *
 * @author Ziad Malik
 * @author Evan Tang
 * @version 1.0
 */
public class Date {
    private final int year;
    private final int month;
    private final int day;

    // Year validation constants
    private static final int MIN_YEAR = 1800;
    private static final int NINETEENTH_CENTURY = 1900;
    private static final int TWENTIETH_CENTURY = 2000;
    private static final int MAX_YEAR = 2026;

    // Month validation constants
    private static final int JANUARY = 1;
    private static final int DECEMBER = 12;

    // Day validation constants
    private static final int MIN_DAY = 1;
    private static final int DAYS_IN_LONG_MONTH = 31;
    private static final int DAYS_IN_SHORT_MONTH = 30;
    private static final int DAYS_IN_FEB_LEAP = 29;
    private static final int DAYS_IN_FEB_NORMAL = 28;

    // Month constants
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

    // Month Code Constants
    // jfmamjjasond: 144025036146
    /**
     * JANUARY and OCTOBER = 1
     * FEBRUARY, MARCH, NOVEMBER = 4
     * APRIL, JULY = 0;
     * MAY = 2
     * JUNE = 5
     * AUGUST = 3
     * SEPTEMBER, DECEMBER = 6
     */
    private static final int JAN_OCT_CODE = 1;
    private static final int FEB_MAR_NOV_CODE = 4;
    private static final int APR_JULY_CODE = 0;
    private static final int MAY_CODE = 2;
    private static final int JUNE_CODE = 5;
    private static final int AUG_CODE = 3;
    private static final int SEP_DEC_CODE = 6;


    // Day of week constants (for day-of-week calculation result)
    private static final int SATURDAY = 0;
    private static final int SUNDAY = 1;
    private static final int MONDAY = 2;
    private static final int TUESDAY = 3;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 5;
    private static final int FRIDAY = 6;

    // Leap year calculation constants
    private static final int LEAP_YEAR_DIVISOR = 4;
    private static final int CENTURY_YEAR_DIVISOR = 100;
    private static final int QUAD_CENTURY_DIVISOR = 400;

    // Day-of-week algorithm constants
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
     *            
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Date(final int year, 
                final int month, 
                final int day) 
    {
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
     * Gets the date in YYYY-MM-DD format (e.g., "2024-09-30").
     * Pads single-digit months and days with a leading zero.
     *
     * @return the date string in format YYYY-MM-DD
     */
    public String getYyyyMmDd() {
        final String yearStr;
        final String monthStr;
        final String dayStr;

        // Convert year to string
        yearStr = String.valueOf(year);

        // Pad month with leading zero if needed (e.g., 3 becomes "03")
        monthStr = month < OCTOBER ? "0" + month : String.valueOf(month);

        // Pad day with leading zero if needed (e.g., 7 becomes "07")
        dayStr = day < OCTOBER ? "0" + day : String.valueOf(day);

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
     * Uses a specific algorithm for dates between 1800-2026.
     *
     * @return the day of the week as a string
     */
    public String getDayOfWeek() {
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

        // Get last two digits of year (e.g., 1977 becomes 77)
        lastTwoDigits = year % CENTURY_YEAR_DIVISOR;

        // Determine century offset based on which century the year falls in
        if (year >= MIN_YEAR && year < NINETEENTH_CENTURY) {
            // For 1800s: add 2
            centuryOffset = CENTURY_1800_OFFSET;
        } else if (year >= TWENTIETH_CENTURY && year <= MAX_YEAR) {
            // For 2000s: add 6
            centuryOffset = CENTURY_2000_OFFSET;
        } else {
            // For 1900s: add 0
            centuryOffset = 0;
        }

        // Determine leap year offset for January and February only
        // If it's a leap year AND the month is Jan or Feb, add 6
        if (isLeapYear(year) && (month == JANUARY || month == FEBRUARY)) {
            leapYearOffset = LEAP_YEAR_JAN_FEB_OFFSET;
        } else {
            leapYearOffset = 0;
        }

        // Step 1: Calculate the number of twelves in last two digits
        step1 = lastTwoDigits / TWELVE_DIVISOR;

        // Step 2: Calculate remainder after removing those twelves
        step2 = lastTwoDigits % TWELVE_DIVISOR;

        // Step 3: Calculate the number of fours in step 2
        step3 = step2 / FOUR_DIVISOR;

        // Step 4: The day of the month
        step4 = day;

        // Step 5: Add month code (each month has a specific code)
        step5 = getMonthCode(month);

        // Step 6: Sum all numbers including offsets, then mod by 7
        // This gives us a number between 0 and 6 representing the day of week
        step6 = centuryOffset + leapYearOffset + step1 + step2 + step3 + step4 + step5;
        dayCode = step6 % SEVEN_DIVISOR;

        // Step 7: Convert the day code (0-6) to actual day name
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
    private static void validateDate(final int year,
                                     final int month,
                                     final int day) {
        // Validate year is within acceptable range
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new IllegalArgumentException("Year must be between Eighteenth Century and the Current Year");
        }

        // Validate month is between JANUARY and DECEMBER
        if (month < JANUARY || month > DECEMBER) {
            throw new IllegalArgumentException("Month must be between JANUARY and DECEMBER");
        }

        // Validate day is at least 1
        if (day < MIN_DAY) {
            throw new IllegalArgumentException("Day must be at least 1");
        }

        // Get the maximum valid day for this specific month and year
        final int maxDaysInMonth;
        maxDaysInMonth = getMaxDaysInMonth(month, year);

        // Validate day doesn't exceed maximum for the month
        if (day > maxDaysInMonth) {
            throw new IllegalArgumentException("Day must be valid for the given month and year");
        }
    }

    /**
     * Gets the maximum number of days in a given month for a given year.
     * Takes into account leap years for February.
     *
     * @param month the month
     * @param year the year
     * @return the maximum number of days in the month
     */
    private static int getMaxDaysInMonth(final int month,
                                         final int year) {
        final int maxDays;

        // February has special handling for leap years
        if (month == FEBRUARY)
        {
            if (isLeapYear(year))
            {
                maxDays = DAYS_IN_FEB_LEAP;
            } else
            {
                maxDays = DAYS_IN_FEB_NORMAL;
            }
        } else if (month == APRIL || month == JUNE ||
                month == SEPTEMBER || month == NOVEMBER)
        {
            // April, June, September, November have 30 days
            maxDays = DAYS_IN_SHORT_MONTH;
        } else
        {
            // January, March, May, July, August, October, December have 31 days
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
    private static boolean isLeapYear(final int year)
    {
        final boolean leap;

        // Check if divisible by 400 (these are always leap years)
        if (year % QUAD_CENTURY_DIVISOR == 0)
        {
             leap = true;
        } else if (year % CENTURY_YEAR_DIVISOR == 0)
        {
            // Divisible by 100 but not 400 (these are NOT leap years)
             leap = false;
        } else if (year % LEAP_YEAR_DIVISOR == 0)
        {
            // Divisible by 4 but not 100 (these ARE leap years)
             leap = true;
        } else
        {
            // Not divisible by 4 (these are NOT leap years)
             leap = false;
        }

        return leap;
    }

    /**
     * Gets the month name for a given month number.
     * This is a helper method used by the public getMonthName() instance method.
     *
     * @param month the month must be between January and December
     * @return the month name (e.g., "January", "February")
     */
    private static String getMonthName(final int month)
    {
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
     * jfmamjjasond: 144025036146
     * JANUARY and OCTOBER = JAN_OCT_CODE
     * FEBRUARY, MARCH, NOVEMBER = FEB_MAR_NOV_CODE
     * APRIL, JULY = APR_JULY_CODE
     * MAY = MAY_CODE
     * JUNE = JUNE_CODE
     * AUGUST = AUG_CODE
     * SEPTEMBER, DECEMBER = SEP_DEC_CODE
     *
     * @param month the month
     * @return the month code
     */
    private static int getMonthCode(final int month)
    {
        return switch (month) {
            case JANUARY, OCTOBER -> JAN_OCT_CODE;
            case FEBRUARY, MARCH, NOVEMBER -> FEB_MAR_NOV_CODE;
            case APRIL, JULY -> APR_JULY_CODE;
            case MAY -> MAY_CODE;
            case JUNE -> JUNE_CODE;
            case AUGUST -> AUG_CODE;
            case SEPTEMBER, DECEMBER -> SEP_DEC_CODE;
            default -> 0;
        };
    }

    /**
     * Converts a day code to a day name.
     *
* @param dayCode the day code correlates to the days of the week from Saturday to Friday
     * @return the day name in lowercase
     */
    private static String getDayName(final int dayCode)
    {
        return switch (dayCode) {
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            default -> "unknown";
        };
    }
}