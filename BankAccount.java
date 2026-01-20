package ca.bcit.comp2522.bank;

/**
 * Represents a bank account with balance, PIN protection, and transaction capabilities.
 * Manages deposits, withdrawals, and account details for a bank client.
 *
 * @author Ziad Malik
 * @version 1.0
 */
public class BankAccount {
    private final BankClient client;
    private double balanceUsd;
    private final int pin;
    private final String accountNumber;
    private final Date accountOpened;
    private final Date accountClosed;

    // Account number length validation constants
    private static final int MIN_ACCOUNT_NUMBER_LENGTH = 6;
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 7;

    // PIN validation constants
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    /**
     * Constructs a BankAccount with the specified information.
     *
     * @param client the bank client who owns this account
     * @param balanceUsd the initial balance in USD
     * @param pin the 4-digit PIN for the account
     * @param accountNumber the account number (must be 6 or 7 characters)
     * @param accountOpened the date the account was opened
     * @param accountClosed the date the account was closed (can be null if still open)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public BankAccount(final BankClient client,
                       final double balanceUsd,
                       final int pin,
                       final String accountNumber,
                       final Date accountOpened,
                       final Date accountClosed) {
        validateBankAccount(client, balanceUsd, pin, accountNumber, accountOpened);
        this.client = client;
        this.balanceUsd = balanceUsd;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.accountOpened = accountOpened;
        this.accountClosed = accountClosed;
    }

    /**
     * Deposits money into the account.
     *
     * @param amountUsd the amount to deposit in USD
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(final double amountUsd) {
        // Validate deposit amount is not negative
        if (amountUsd < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        // Add deposit amount to current balance
        balanceUsd += amountUsd;
    }

    /**
     * Withdraws money from the account without PIN verification.
     *
     * @param amountUsd the amount to withdraw in USD
     * @throws IllegalArgumentException if amount is negative or exceeds balance
     */
    public void withdraw(final double amountUsd) {
        // Validate withdrawal amount is not negative
        if (amountUsd < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }

        // Validate sufficient funds are available
        if (amountUsd > balanceUsd) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Subtract withdrawal amount from current balance
        balanceUsd -= amountUsd;
    }

    /**
     * Withdraws money from the account with PIN verification.
     *
     * @param amountUsd the amount to withdraw in USD
     * @param pinToMatch the PIN to verify
     * @throws IllegalArgumentException if amount is invalid, PIN is incorrect, or insufficient funds
     */
    public void withdraw(final double amountUsd, final int pinToMatch) {
        // Verify PIN matches before allowing withdrawal
        if (pinToMatch != pin) {
            throw new IllegalArgumentException("Invalid PIN");
        }

        // Call the other withdraw method to perform the actual withdrawal
        withdraw(amountUsd);
    }

    /**
     * Gets detailed information about the account.
     *
     * @return a formatted string with account details
     */
    public String getDetails() {
        final String clientName;
        final String openDay;
        final String openMonthName;
        final int openDayNum;
        final int openYear;
        final String details;
        final int balanceInt;
        final String capitalizedOpenDay;

        // Determine verb tense based on whether client is alive
        // Use "has" for alive clients, "had" for deceased clients
        clientName = client.isAlive() ?
                client.getName().getFullName() + " has" :
                client.getName().getFullName() + " had";

        // Convert balance to integer (remove decimal portion)
        balanceInt = (int) balanceUsd;

        // Extract account opened date components
        openDay = accountOpened.getDayOfWeek();
        openMonthName = accountOpened.getMonthName();
        openDayNum = accountOpened.getDay();
        openYear = accountOpened.getYear();

        // Capitalize first letter of day name
        capitalizedOpenDay = Character.toUpperCase(openDay.charAt(0)) + openDay.substring(1);

        // Build details string based on whether account is closed
        if (accountClosed == null) {
            // Account is still open
            details = clientName + " $" + balanceInt + " USD in account #" + accountNumber +
                    " which they opened on " +
                    capitalizedOpenDay + " " + openMonthName + " " + openDayNum + ", " + openYear + ".";
        } else {
            // Account is closed
            final String closeDay;
            final String closeMonthName;
            final int closeDayNum;
            final int closeYear;
            final String capitalizedCloseDay;

            // Extract account closed date components
            closeDay = accountClosed.getDayOfWeek();
            closeMonthName = accountClosed.getMonthName();
            closeDayNum = accountClosed.getDay();
            closeYear = accountClosed.getYear();

            // Capitalize first letter of close day name
            capitalizedCloseDay = Character.toUpperCase(closeDay.charAt(0)) + closeDay.substring(1);

            // Build details string with both open and close dates
            details = clientName + " $" + balanceInt + " USD in account #" + accountNumber +
                    " which they opened on " + capitalizedOpenDay + " " + openMonthName + " " +
                    openDayNum + ", " + openYear + " and closed " + capitalizedCloseDay + " " +
                    closeMonthName + " " + closeDayNum + ", " + closeYear + ".";
        }

        return details;
    }

    /**
     * Validates the bank account parameters.
     *
     * @param client the bank client
     * @param balanceUsd the initial balance
     * @param pin the PIN
     * @param accountNumber the account number
     * @param accountOpened the date account was opened
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private static void validateBankAccount(final BankClient client,
                                            final double balanceUsd,
                                            final int pin,
                                            final String accountNumber,
                                            final Date accountOpened) {
        // Validate client is not null
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        // Validate balance is not negative
        if (balanceUsd < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        // Validate PIN is a 4-digit number (1000-9999)
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }

        // Validate account number is not null or blank
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }

        // Validate account number length is 6 or 7 characters
        if (accountNumber.length() < MIN_ACCOUNT_NUMBER_LENGTH ||
                accountNumber.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Account number must be 6 or 7 characters");
        }

        // Validate account opened date is not null
        if (accountOpened == null) {
            throw new IllegalArgumentException("Account opened date cannot be null");
        }
    }
}