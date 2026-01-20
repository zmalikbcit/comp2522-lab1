package ca.bcit.comp2522.bank;

/**
 * Main class to demonstrate the banking system with historical figures.
 *
 * @author Ziad Malik
 * @author Evan Tang
 * @version 1.0
 */
public class Main {
    /**
     * Main method that creates and tests bank accounts.
     *
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        // Albert Einstein
        final Name einsteinName;
        final Date einsteinBirth;
        final Date einsteinDeath;
        final Date einsteinSignup;
        final BankClient einsteinClient;
        final Date einsteinAccountOpened;
        final Date einsteinAccountClosed;
        final BankAccount einsteinAccount;

        einsteinName = new Name("Albert", "Einstein");
        einsteinBirth = new Date(1879, 3, 14);
        einsteinDeath = new Date(1955, 4, 18);
        einsteinSignup = new Date(1900, 1, 1);
        einsteinClient = new BankClient(einsteinName, einsteinBirth,
                einsteinDeath, "abc123", einsteinSignup);
        einsteinAccountOpened = new Date(1900, 1, 1);
        einsteinAccountClosed = new Date(1950, 10, 14);
        einsteinAccount = new BankAccount(einsteinClient, 1000, 3141,
                "abc123", einsteinAccountOpened, einsteinAccountClosed);

        System.out.println(einsteinName.getInitials());
        System.out.println(einsteinName.getFullName());
        System.out.println(Name.getReverseName("Albert", "Einstein"));
        System.out.println(einsteinClient.getDetails());
        einsteinAccount.withdraw(100);
        System.out.println(einsteinAccount.getDetails());
        System.out.println();

        // Nelson Mandela
        final Name mandelaName;
        final Date mandelaBirth;
        final Date mandelaDeath;
        final Date mandelaSignup;
        final BankClient mandelaClient;
        final Date mandelaAccountOpened;
        final BankAccount mandelaAccount;

        mandelaName = new Name("Nelson", "Mandela");
        mandelaBirth = new Date(1918, 7, 18);
        mandelaDeath = new Date(2013, 12, 5);
        mandelaSignup = new Date(1994, 5, 10);
        mandelaClient = new BankClient(mandelaName, mandelaBirth,
                mandelaDeath, "654321", mandelaSignup);
        mandelaAccountOpened = new Date(1994, 5, 10);
        mandelaAccount = new BankAccount(mandelaClient, 2000, 4664,
                "654321", mandelaAccountOpened, null);

        System.out.println(mandelaName.getInitials());
        System.out.println(mandelaName.getFullName());
        System.out.println(Name.getReverseName("Nelson", "Mandela"));
        System.out.println(mandelaClient.getDetails());
        mandelaAccount.withdraw(200);
        System.out.println(mandelaAccount.getDetails());
        System.out.println();

        // Frida Kahlo
        final Name kahloName;
        final Date kahloBirth;
        final Date kahloDeath;
        final Date kahloSignup;
        final BankClient kahloClient;
        final Date kahloAccountOpened;
        final Date kahloAccountClosed;
        final BankAccount kahloAccount;

        kahloName = new Name("Frida", "Kahlo");
        kahloBirth = new Date(1907, 7, 6);
        kahloDeath = new Date(1954, 7, 13);
        kahloSignup = new Date(1940, 1, 1);
        kahloClient = new BankClient(kahloName, kahloBirth,
                kahloDeath, "frd123", kahloSignup);
        kahloAccountOpened = new Date(1940, 1, 1);
        kahloAccountClosed = new Date(1954, 7, 13);
        kahloAccount = new BankAccount(kahloClient, 500, 1907,
                "frd123", kahloAccountOpened, kahloAccountClosed);

        System.out.println(kahloName.getInitials());
        System.out.println(kahloName.getFullName());
        System.out.println(Name.getReverseName("Frida", "Kahlo"));
        System.out.println(kahloClient.getDetails());
        kahloAccount.withdraw(50);
        System.out.println(kahloAccount.getDetails());
        System.out.println();

        // Jackie Chan
        final Name chanName;
        final Date chanBirth;
        final Date chanSignup;
        final BankClient chanClient;
        final Date chanAccountOpened;
        final BankAccount chanAccount;

        chanName = new Name("Jackie", "Chan");
        chanBirth = new Date(1954, 4, 7);
        chanSignup = new Date(1980, 10, 1);
        chanClient = new BankClient(chanName, chanBirth,
                null, "chan789", chanSignup);
        chanAccountOpened = new Date(1980, 10, 1);
        chanAccount = new BankAccount(chanClient, 3000, 1954,
                "chan789", chanAccountOpened, null);

        System.out.println(chanName.getInitials());
        System.out.println(chanName.getFullName());
        System.out.println(Name.getReverseName("Jackie", "Chan"));
        System.out.println(chanClient.getDetails());
        chanAccount.withdraw(500);
        System.out.println(chanAccount.getDetails());
    }
}