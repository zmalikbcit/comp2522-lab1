package ca.bcit.comp2522.bank;

/**
 * Represents a person's name with first and last components.
 * Provides methods for name manipulation including initials, full name formatting, and reversal.
 *
 * @author Ziad Malik
 * @version 1.0
 */
public final class Name {
    private final String first;
    private final String last;

    // Symbolic constant for maximum name length validation
    private static final int MAX_NAME_LENGTH = 45;

    /**
     * Constructs a Name object with the specified first and last names.
     * Names must be non-null, non-blank, under 45 characters, and cannot contain "admin".
     *
     * @param first the first name
     * @param last the last name
     * @throws IllegalArgumentException if first or last name is null, blank, too long, or contains "admin"
     */
    public Name(final String first, final String last) {
        validateName(first, last);
        this.first = first;
        this.last = last;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirst(){
        return first;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLast() {
        return last;
    }

    /**
     * Gets the initials of the name in the format "F.L." (e.g., "T.W." for Tiger Woods).
     * The initials are automatically capitalized.
     *
     * @return the initials with periods in between
     */
    public String getInitials(){
        final char firstInitial;
        final char lastInitial;

        // Extract first character from each name and capitalize
        firstInitial = Character.toUpperCase(getFirst().charAt(0));
        lastInitial = Character.toUpperCase(getLast().charAt(0));

        return firstInitial + "." + lastInitial + ".";
    }

    /**
     * Gets the full name in proper case format (e.g., "Tiger Woods" from "tigER wooDS").
     * First letter of each name is capitalized, rest are lowercase.
     *
     * @return the formatted full name
     */
    public String getFullName(){
        final String firstFormatted;
        final String lastFormatted;

        // Capitalize first letter, lowercase the rest
        firstFormatted = getInitials().charAt(0) + first.substring(1).toLowerCase();
        lastFormatted = getInitials().charAt(2) + last.substring(1).toLowerCase();

        return firstFormatted + " " + lastFormatted;
    }

    /**
     * Gets the reversed version of a name.
     * Each name is reversed individually, and the order is swapped (last first).
     *
     * @param first the first name to reverse
     * @param last the last name to reverse
     * @return the reversed name in the format "reversedLast reversedFirst"
     */
    public static String getReverseName(final String first, final String last) {
        final String reversedFirst;
        final String reversedLast;
        final StringBuilder builderFirst;
        final StringBuilder builderLast;

        builderFirst = new StringBuilder();
        builderLast = new StringBuilder();

        // Use StringBuilder's built-in reverse method
        builderFirst.append(first).reverse();
        builderLast.append(last).reverse();

        // Convert StringBuilder objects back to Strings
        reversedFirst = builderFirst.toString();
        reversedLast = builderLast.toString();

        // Return in reversed order: last name first
        return reversedLast + " " + reversedFirst;
    }

    /**
     * Validates the first and last names according to business rules.
     *
     * @param first the first name to validate
     * @param last the last name to validate
     * @throws IllegalArgumentException if first or last name is null, empty, longer than 45 characters,
     *                                  or contains the word "admin" in any case
     */
    private static void validateName(final String first, final String last) {
        // Validate first name: not null or blank
        if  (first == null || first.isBlank()) {
            throw new IllegalArgumentException("first cannot be null or empty");
        }

        // Validate first name: length constraint
        if (first.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("first cannot be longer than 45 characters");
        }

        // Validate first name: no "admin" keyword (case-insensitive)
        if (first.toLowerCase().contains("admin")){
            throw new IllegalArgumentException("first cannot contain admin");
        }

        // Validate last name: not null or blank
        if  (last == null || last.isBlank()) {
            throw new IllegalArgumentException("last cannot be null or empty");
        }

        // Validate last name: length constraint
        if (last.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("last cannot be longer than 45 characters");
        }

        // Validate last name: no "admin" keyword (case-insensitive)
        if (last.toLowerCase().contains("admin")){
            throw new IllegalArgumentException("last cannot contain admin");
        }
    }
}