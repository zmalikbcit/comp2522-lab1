package ca.bcit.comp2522.bank;

/**
 * Class that does name manipulation and validation
 *
 * @author Ziad Malik
 * @version 1.0
 */
public final class Name {
    private final String first;
    private final String last;
    private static final int MAX_NAME_LENGTH = 45;

    public Name(final String first, final String last) {
        validateName(first, last);
        this.first = first;
        this.last = last;
    }

    public String getFirst(){
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getInitials(){
        final char firstInitial;
        final char lastInitial;

        firstInitial = Character.toUpperCase(first.charAt(0));
        lastInitial = Character.toUpperCase(last.charAt(0));

        return firstInitial + "." + lastInitial + ".";
    }

    public String getFullName(){
        final String firstFormatted;
        final String lastFormatted;

        firstFormatted = getFirst().charAt(0) + first.substring(1).toLowerCase();
        lastFormatted = getFirst().charAt(2) + last.substring(1).toLowerCase();


        return firstFormatted + " " + lastFormatted;
    }

    public static String getReverseName(final String first, final String last) {
        final String reversedFirst;
        final String reversedLast;
        final StringBuilder builderFirst;
        final StringBuilder builderLast;

        builderFirst = new StringBuilder();
        builderLast = new StringBuilder();

        for (int i = first.length() - 1; i >= 0; i--) {
            builderFirst.append(first.charAt(i));
        }
        for (int i = last.length() - 1; i >= 0; i--) {
            builderLast.append(last.charAt(i));
        }

        reversedFirst = builderFirst.toString();
        reversedLast = builderLast.toString();

        return reversedLast + " " + reversedFirst;


    }

    /**
     *
     * @param first first name
     * @param last last name
     * @throws IllegalArgumentException if first or last name is null, empty, longer than 45 characters
     *                                  or contains the word admin.
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
