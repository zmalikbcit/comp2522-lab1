package ca.bcit.comp2522.bank;

/**
 * Represents a bank client with personal information and account details.
 *
 * @author Ziad Malik
 * @version 1.0
 */
public class BankClient {
    private final Name name;
    private final Date dateBorn;
    private final Date dateDied;
    private final String clientId;
    private final Date signupDate;

    private static final int MIN_CLIENT_ID_LENGTH = 6;
    private static final int MAX_CLIENT_ID_LENGTH = 7;

    /**
     * Constructs a BankClient with the specified information.
     *
     * @param name the client's name
     * @param dateBorn the client's birthdate
     * @param dateDied the client's death date (can be null if alive)
     * @param clientId the client ID (must be 6 or 7 characters)
     * @param signupDate the date the client signed up
     * @throws IllegalArgumentException if name or dateBorn is null, or if clientId is invalid
     */
    public BankClient(final Name name,
                      final Date dateBorn,
                      final Date dateDied,
                      final String clientId,
                      final Date signupDate) {
        validateBankClient(name, dateBorn, clientId, signupDate);
        this.name = name;
        this.dateBorn = dateBorn;
        this.dateDied = dateDied;
        this.clientId = clientId;
        this.signupDate = signupDate;
    }

    /**
     * Checks if the client is alive.
     *
     * @return true if the client is alive, false otherwise
     */
    public boolean isAlive() {
        return dateDied == null;
    }

    /**
     * Gets detailed information about the client.
     * Format: "FirstName LastName client #clientId (alive/died dayOfWeek, Month day, year)
     *          joined the bank on dayOfWeek, Month day, year"
     *
     * @return a formatted string with client details
     */
    public String getDetails() {
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

        fullName = name.getFullName();

        signupDay = signupDate.getDayOfTheWeek();
        signupMonthName = signupDate.getMonthName();
        signupDayNum = signupDate.getDay();
        signupYear = signupDate.getYear();

        birthDay = dateBorn.getDayOfTheWeek();
        birthMonthName = dateBorn.getMonthName();
        birthDayNum = dateBorn.getDay();
        birthYear = dateBorn.getYear();

        if (isAlive()) {
            lifeStatus = "(alive) was born on " + birthDay + ", " + birthMonthName + " " +
                    birthDayNum + ", " + birthYear + "!";
        } else {
            final String deathDay;
            final String deathMonthName;
            final int deathDayNum;
            final int deathYear;

            deathDay = dateDied.getDayOfTheWeek();
            deathMonthName = dateDied.getMonthName();
            deathDayNum = dateDied.getDay();
            deathYear = dateDied.getYear();

            lifeStatus = "(died " + deathDay + ", " + deathMonthName + " " +
                    deathDayNum + ", " + deathYear + ") was born on " +
                    birthDay + ", " + birthMonthName + " " + birthDayNum + ", " + birthYear + "!";
        }

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
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private static void validateBankClient(final Name name,
                                           final Date dateBorn,
                                           final String clientId,
                                           final Date signupDate) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (dateBorn == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        if (signupDate == null) {
            throw new IllegalArgumentException("Signup date cannot be null");
        }

        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("Client ID cannot be null or blank");
        }

        if (clientId.length() < MIN_CLIENT_ID_LENGTH || clientId.length() > MAX_CLIENT_ID_LENGTH) {
            throw new IllegalArgumentException("Client ID must be 6 or 7 characters");
        }
    }
}