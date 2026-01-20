package ca.bcit.comp2522.bank;

/**
 * Represents a person's name with first and last components.
 *
 * @author Ziad Malik
 * @author  Evan Tang
 * @version 1.0
 */
public final class Name {
    private final String first;
    private final String last;

    // Symbolic constant for maximum name length validation
    private static final int MAX_NAME_LENGTH = 76;

    /**
     * Constructs a Name object with the specified first and last names.
     *
     * @param first the first name
     * @param last the last name
     * @throws IllegalArgumentException if first or last name is not valid
     * */
    public Name(final String first,
                final String last) {
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

        builderFirst.append(first).reverse();
        builderLast.append(last).reverse();

        // Convert StringBuilder objects back to Strings
        reversedFirst = builderFirst.toString();
        reversedLast = builderLast.toString();

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
        if  (first == null || first.isBlank()) {
            throw new IllegalArgumentException("first cannot be null or empty");
        }

        if (first.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("first cannot be longer than 45 characters");
        }

        if (first.toLowerCase().contains("admin")){
            throw new IllegalArgumentException("first cannot contain admin");
        }

        if  (last == null || last.isBlank()) {
            throw new IllegalArgumentException("last cannot be null or empty");
        }

        if (last.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("last cannot be longer than 45 characters");
        }

        if (last.toLowerCase().contains("admin")){
            throw new IllegalArgumentException("last cannot contain admin");
        }
    }
}