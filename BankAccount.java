package ca.bcit.comp2522.bank;

/**
 * Represents a bank account with balance, PIN protection, and transaction capabilities.
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

    private static final int MIN_ACCOUNT_NUMBER_LENGTH = 6;
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 7;
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
        if (amountUsd < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        balanceUsd += amountUsd;
    }

    /**
     * Withdraws money from the account without PIN verification.
     *
     * @param amountUsd the amount to withdraw in USD
     * @throws IllegalArgumentException if amount is negative or exceeds balance
     */
    public void withdraw(final double amountUsd) {
        if (amountUsd < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        if (amountUsd > balanceUsd) {
            throw new IllegalArgumentException("Insufficient funds");
        }
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
        if (pinToMatch != pin) {
            throw new IllegalArgumentException("Invalid PIN");
        }
        withdraw(amountUsd);
    }

    /**
     * Gets detailed information about the account.
     * Format depends on whether the account is closed or open.
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
        final String capitalizedCloseDay;


        clientName = client.isAlive() ?
                getName().getFullName() + " has" :
                getName().getFullName() + " had";

        balanceInt = (int) balanceUsd;

        openDay = accountOpened.getDayOfTheWeek();
        openMonthName = accountOpened.getMonthName();
        openDayNum = accountOpened.getDay();
        openYear = accountOpened.getYear();
        capitalizedOpenDay = Character.toUpperCase(openDay.charAt(0)) + openDay.substring(1);

        if (accountClosed == null) {
            details = clientName + " $" + balanceInt + " USD in account #" + accountNumber +
                    " which " + (client.isAlive() ? "they" : "he") + " opened on " +
                    capitalizedOpenDay + " " + openMonthName + " " + openDayNum + ", " + openYear + ".";
        } else {
            final String closeDay;
            final String closeMonthName;
            final int closeDayNum;
            final int closeYear;


            closeDay = accountClosed.getDayOfTheWeek();
            closeMonthName = accountClosed.getMonthName();
            closeDayNum = accountClosed.getDay();
            closeYear = accountClosed.getYear();
            capitalizedCloseDay = Character.toUpperCase(closeDay.charAt(0)) + closeDay.substring(1);

            details = clientName + " $" + balanceInt + " USD in account #" + accountNumber +
                    " which he opened on " + capitalizedOpenDay + " " + openMonthName + " " +
                    openDayNum + ", " + openYear + " and closed " + capitalizedCloseDay + " " +
                    closeMonthName + " " + closeDayNum + ", " + closeYear + ".";
        }

        return details;
    }

    /**
     * Gets the client's Name object.
     *
     * @return the client's Name
     */
    private Name getName() {
        final Name clientName;
        final String firstName;
        final String lastName;

        firstName = client.getDetails().split(" ")[0];
        lastName = client.getDetails().split(" ")[1];

        clientName = new Name(firstName, lastName);

        return clientName;
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
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (balanceUsd < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }

        if (accountNumber.length() < MIN_ACCOUNT_NUMBER_LENGTH ||
                accountNumber.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Account number must be 6 or 7 characters");
        }

        if (accountOpened == null) {
            throw new IllegalArgumentException("Account opened date cannot be null");
        }
    }
}