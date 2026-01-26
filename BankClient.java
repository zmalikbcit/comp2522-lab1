package ca.bcit.comp2522.bank;

/**
 * Represents a bank client with personal information and account details.
 * Stores the client's name, birth/death dates, unique ID, and signup date.
 *
 * @author Ziad Malik
 * @author Evan Tang
 * @version 1.0
 */
public class BankClient
{
    private final Name name;
    private final Date dateBorn;
    private final Date dateDied;
    private final String clientId;
    private final Date signupDate;

    // Client ID length validation constants
    private static final int MIN_CLIENT_ID_LENGTH = 6;
    private static final int MAX_CLIENT_ID_LENGTH = 7;

    /**
     * Constructs a BankClient with the specified information.
     *
     * @param name the client's name
     * @param dateBorn the client's birthdate
     * @param dateDied the client's death date (can be null if alive)
     * @param clientId the client ID must be between MIN_CLIENT_ID_LENGTH and MAX_CLIENT_ID_LENGTH
     * @param signupDate the date the client signed up
     */
    public BankClient(final Name name,
                      final Date dateBorn,
                      final Date dateDied,
                      final String clientId,
                      final Date signupDate)
    {
        validateBankClient(name, dateBorn, clientId, signupDate);

        this.name = name;
        this.dateBorn = dateBorn;
        this.dateDied = dateDied;
        this.clientId = clientId;
        this.signupDate = signupDate;
    }

    /**
     * Gets the client's Name object.
     *
     * @return the client's name
     */
    public Name getName() {
        return name;
    }

    /**
     * Checks if the client is alive.
     * A client is considered alive if their death date is null.
     *
     * @return true if the client is alive, false otherwise
     */
    public boolean isAlive() {
        return dateDied == null;
    }

    /**
     * Gets detailed information about the client in a formatted string.
     *
     * @return a formatted string with client details
     */
    public String getDetails()
    {
        final String fullName;
        final String lifeStatus;

        final String signupDay;
        final String signupMonthName;
        final int signupDayNum;
        final int signupYear;

        final String birthDay;
        final String birthMonthName;
        final int birthDayNum;
        final int birthYear;

        final String details;

        // Get the formatted full name
        fullName = name.getFullName();

        // Extract signup date components
        signupDay = signupDate.getDayOfWeek();
        signupMonthName = signupDate.getMonthName();
        signupDayNum = signupDate.getDay();
        signupYear = signupDate.getYear();

        // Extract birthdate components
        birthDay = dateBorn.getDayOfWeek();
        birthMonthName = dateBorn.getMonthName();
        birthDayNum = dateBorn.getDay();
        birthYear = dateBorn.getYear();

        // Build life status string based on whether client is alive or deceased
        if (isAlive())
        {
            // Client is alive: show only birth information
            lifeStatus = "(alive) was born on " + birthDay + ", " + birthMonthName + " " +
                    birthDayNum + ", " + birthYear + "!";
        } else
        {
            // Client is deceased: show both death and birth information
            final String deathDay;
            final String deathMonthName;
            final int deathDayNum;
            final int deathYear;

            // Extract death date components
            deathDay = dateDied.getDayOfWeek();
            deathMonthName = dateDied.getMonthName();
            deathDayNum = dateDied.getDay();
            deathYear = dateDied.getYear();

            // Build status with death information first, then birth
            lifeStatus = "(died " + deathDay + ", " + deathMonthName + " " +
                    deathDayNum + ", " + deathYear + ") was born on " +
                    birthDay + ", " + birthMonthName + " " + birthDayNum + ", " + birthYear + "!";
        }

        // Combine all components into final details string
        details = fullName + " client #" + clientId + " " + lifeStatus +
                " joined the bank on " + signupDay + ", " + signupMonthName + " " +
                signupDayNum + ", " + signupYear;

        return details;
    }

    /**
     * Validates the bank client parameters.
     *
     * @param name the client's name
     * @param dateBorn the client's birthdate
     * @param clientId the client ID
     * @param signupDate the signup date
     */
    private static void validateBankClient(final Name name,
                                           final Date dateBorn,
                                           final String clientId,
                                           final Date signupDate)
    {
        // Validate name is not null
        if (name == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }

        // Validate birthdate is not null
        if (dateBorn == null)
        {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        // Validate signup date is not null
        if (signupDate == null)
        {
            throw new IllegalArgumentException("Signup date cannot be null");
        }

        // Validate client ID is not null or blank
        if (clientId == null || clientId.isBlank())
        {
            throw new IllegalArgumentException("Client ID cannot be null or blank");
        }

        // Validate client ID length is 6 or 7 characters
        if (clientId.length() < MIN_CLIENT_ID_LENGTH ||
                clientId.length() > MAX_CLIENT_ID_LENGTH)
        {
            throw new IllegalArgumentException("Client ID must be " +
                    MIN_CLIENT_ID_LENGTH + " or " + MAX_CLIENT_ID_LENGTH + " characters");
        }
    }
}